package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CustomExceptions.FigmaDesignException;
import com.ucl.imaginethisserver.DAO.ConversionDao;
import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.FigmaComponents.*;
import com.ucl.imaginethisserver.Model.Conversion;
import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Util.*;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.Service.GenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GenerationServiceImpl implements GenerationService {

    private final FigmaAPIUtil figmaAPIUtil;
    private final CodeGenerator codeGenerator;
    private final FileUtil fileUtil;
    private final ExpoUtil expoUtil;
    private final ConversionDao conversionDao;
    private final ProjectDao projectDao;
    private final Logger logger = LoggerFactory.getLogger(GenerationServiceImpl.class);

    @Value("${config.outputStorageFolder}")
    private String outputStorageFolder;

    @Autowired
    public GenerationServiceImpl(FigmaAPIUtil figmaAPIUtil,
                                 CodeGenerator codeGenerator,
                                 FileUtil fileUtil,
                                 ExpoUtil expoUtil,
                                 ConversionDao conversionDao,
                                 ProjectDao projectDao) {
        this.figmaAPIUtil = figmaAPIUtil;
        this.codeGenerator = codeGenerator;
        this.fileUtil = fileUtil;
        this.expoUtil = expoUtil;
        this.conversionDao = conversionDao;
        this.projectDao = projectDao;
    }


    /**
     * @param projectID
     * @param auth
     * @param wireframeList
     */
    public boolean buildProject(String projectID, Authentication auth, List<String> wireframeList, boolean publish) {

        logger.info("Starting build of project {}", projectID);

        FigmaFile figmaFile = getFigmaFile(projectID, auth);

        if (figmaFile == null) {
            logger.error("Could not find Figma design for project {}", projectID);
            throw new NotFoundException("Project " + projectID + " not found.");
        }

        // Filter out only selected wireframes
        wireframeList = wireframeList.stream().map(name -> Wireframe.convertToWireframeName(name)).collect(Collectors.toList());
        figmaFile.filterWireframesByName(wireframeList);
        if (figmaFile.getWireframes().isEmpty()) {
            logger.error("No matching wireframes selected in project {}", projectID);
            throw new FigmaDesignException("No wireframes to generate.");
        }

        // Add project to database if not existing already, update if exists
        Project project = projectDao.getProjectByID(projectID);
        if (project == null) {
            logger.info("Adding new project record to database.");
            project = new Project();
            project.setProjectId(projectID);
            project.setProjectName(figmaFile.getProjectName());
            projectDao.addProject(project);
        } else {
            project.setProjectName(figmaFile.getProjectName());
            projectDao.updateProject(projectID, project);
        }

        // Add conversion record to database
        logger.info("Adding new conversion record to database.");
        Conversion conversion = new Conversion();
        conversion.setProjectId(projectID);
        conversion.setUserId(auth.getUserID());
        conversion.setConversionId(UUID.randomUUID());
        conversion.setTimestamp(System.currentTimeMillis());
        conversion.setConversionStatus(ConversionStatus.RUNNING);
        conversion.setPublishStatus(publish ? ConversionStatus.NOT_STARTED : ConversionStatus.NOT_TRIGGERED);
        conversionDao.addNewConversion(conversion);

        // Start writing process
        try {
            codeGenerator.generateOutputFolder(figmaFile);
            codeGenerator.generatePackageFiles(figmaFile);
            codeGenerator.generateWireframes(figmaFile);
            codeGenerator.generateReusableComponents(figmaFile);
            codeGenerator.generateAppJSCode(figmaFile);
        } catch (IOException e) {
            logger.error("Error during code generation.", e);
            conversion.setConversionStatus(ConversionStatus.FAILED);
            conversionDao.updateConversion(UUID.fromString(conversion.getConversionId().toString()), conversion);
            return false;
        }

        // Zip the folder where project's source code resides for downloads
        String projectFolder = String.format("%s/%s", outputStorageFolder, projectID);
        fileUtil.zipDirectory(projectFolder);

        conversion.setConversionStatus(ConversionStatus.SUCCEEDED);
        conversionDao.updateConversion(UUID.fromString(conversion.getConversionId().toString()), conversion);

        // Publish project to Expo through a new Docker container job
        if (publish) expoUtil.publish(projectID, figmaFile.getProjectName());

        return true;
    }


    public File downloadProject(String projectID) {
        String projectFolder = String.format("%s/%s", outputStorageFolder, projectID);
        String projectZipFile = String.format("%s/%s.zip", outputStorageFolder, projectID);

        // If project has not been converted yet, return nothing
        if (!fileUtil.directoryExists(projectFolder)) return null;
        if (!fileUtil.fileExists(projectZipFile)) return null;

        return new File(projectZipFile);
    }


    /**
     * Helper method for getting specific project's Figma file and
     * parsing the JSON response as FigmaFile object.
     * @param projectID - Figma project's ID
     * @param auth - credentials for authentications
     * @return figmaFile - FigmaFile object
     */
    public FigmaFile getFigmaFile(String projectID, Authentication auth) {
        JsonObject rawFigmaFile;
        try {
            rawFigmaFile = figmaAPIUtil.requestFigmaFile(projectID, auth);
        } catch (IOException e) { return null; }

        JsonObject rawFigmaDocument = rawFigmaFile.get("document").getAsJsonObject();

        FigmaFile figmaFile = new FigmaFile(projectID);
        figmaFile.setProjectName(rawFigmaFile.get("name").getAsString());
        figmaFile.setLastModified(rawFigmaFile.get("lastModified").getAsString());
        figmaFile.setVersion(rawFigmaFile.get("version").getAsString());

        for (JsonElement pageJson : rawFigmaDocument.get("children").getAsJsonArray()) {
            Page page = new Gson().fromJson(pageJson, Page.class);
            for (JsonElement jsonComponent : page.getChildren()) {
                // We are interested only in wireframes
                if (!jsonComponent.getAsJsonObject().get("type").getAsString().equals("FRAME")) continue;
                Wireframe wireframe = new Gson().fromJson(jsonComponent, Wireframe.class);
                // Further recursively process components in a wireframe
                wireframe.setComponents(processJsonComponents(wireframe.getChildren()));
                page.addWireframe(wireframe);
            }
            figmaFile.addPage(page);
        }

        // Add image URLs to wireframes and components as batch request for optimisation by reducing slow Figma API calls
        List<String> ids = new ArrayList<>();
        for (Wireframe wireframe : figmaFile.getWireframes()) {
            ids.add(wireframe.getId());
        }
        for (FigmaComponent component : figmaFile.getAllComponents()) {
            ids.add(component.getId());
        }
        Map<String, String> imageURLs = figmaAPIUtil.requestComponentImageURLs(projectID, auth, ids);
        for (Wireframe wireframe : figmaFile.getWireframes()) {
            wireframe.setImageURL(imageURLs.get(wireframe.getId()));
        }
        for (FigmaComponent component : figmaFile.getAllComponents()) {
            component.setImageURL(imageURLs.get(component.getId()));
        }

        // Special cases
        for (FigmaComponent component : figmaFile.getComponents()) {
            component.setFigmaFile(figmaFile);
            if (component instanceof Image) {
                continue; // TODO: Process or download images
            }
        }
        return figmaFile;
    }


    public List<FigmaComponent> processJsonComponents(JsonArray jsonComponents) {
        List<FigmaComponent> figmaComponents = new ArrayList<>();

        // Process all children
        for (JsonElement jsonChild : jsonComponents) {

            String type = jsonChild.getAsJsonObject().get("type").getAsString();
            String name = jsonChild.getAsJsonObject().get("name").getAsString();

            // Parse individual JSON objects to correct FigmaComponent objects based on heuristics
            FigmaComponent component;
            if (type.equals("TEXT")) {
                component = new Gson().fromJson(jsonChild, Text.class);

            } else if (type.equals("VECTOR")) {
                component = new Gson().fromJson(jsonChild, Vector.class);

            } else if (type.equals("ELLIPSE")) {
                component = new Gson().fromJson(jsonChild, Ellipse.class);

            } else if (name.contains("switch")) {
                component = new Gson().fromJson(jsonChild, Switch.class);

            } else if (type.equals("RECTANGLE") && name.contains("map")) {
                component = new Gson().fromJson(jsonChild, FigmaMap.class);

            } else if (type.equals("GROUP") && name.contains("textbutton")) {
                component = new Gson().fromJson(jsonChild, Button.class);

            } else if (type.equals("GROUP") && name.contains("input")) {
                component = new Gson().fromJson(jsonChild, TextBox.class);

            } else if (type.equals("GROUP") && name.contains("navigation")) {
                component = new Gson().fromJson(jsonChild, Navigation.class);

            } else if (type.equals("GROUP") && name.matches("form|card")) {
                component = new Gson().fromJson(jsonChild, Form.class);

            } else if (type.equals("GROUP") && name.contains("slider")) {
                component = new Gson().fromJson(jsonChild, Slider.class);

            } else if (type.equals("GROUP") && name.contains("imagebutton")) {
                component = new Gson().fromJson(jsonChild, ImageButton.class);

            } else if (type.equals("GROUP") && name.contains("chart")) {
                component = new Gson().fromJson(jsonChild, Chart.class);

            } else if (type.equals("GROUP") && name.contains("dropdown")) {
                component = new Gson().fromJson(jsonChild, Dropdown.class);

            } else if (name.matches(".*(image|picture|icon).*")) {
                component = new Gson().fromJson(jsonChild, Image.class);

            } else if (type.equals("RECTANGLE")) {
                component = new Gson().fromJson(jsonChild, Rectangle.class);

            } else {
                String componentID = jsonChild.getAsJsonObject().get("id").getAsString();
                logger.warn("Could not recognize an element with ID {}", componentID);
                continue;
            }

            // Recursively parse all children components as well
            if (component instanceof Group) {
                ((Group) component).setComponents(processJsonComponents(((Group) component).getChildren()));
            }

            figmaComponents.add(component);
        }

        return figmaComponents;
    }

}
