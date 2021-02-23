package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.FigmaComponents.*;
import com.ucl.imaginethisserver.Util.CodeGenerator;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.Service.GenerationService;
import com.ucl.imaginethisserver.Util.Authentication;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import com.ucl.imaginethisserver.Util.FileUtil;
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

@Service
public class GenerationServiceImpl implements GenerationService {

    private final FigmaAPIUtil figmaAPIUtil;
    private final CodeGenerator codeGenerator;
    private final FileUtil fileUtil;
    private final Logger logger = LoggerFactory.getLogger(GenerationServiceImpl.class);

    @Value("${config.outputStorageFolder}")
    private String outputStorageFolder;

    @Autowired
    public GenerationServiceImpl(FigmaAPIUtil figmaAPIUtil, CodeGenerator codeGenerator, FileUtil fileUtil) {
        this.figmaAPIUtil = figmaAPIUtil;
        this.codeGenerator = codeGenerator;
        this.fileUtil = fileUtil;
    }


    /**
     * @param projectID
     * @param auth
     * @param wireframeList
     */
    public boolean buildProject(String projectID, Authentication auth, List<String> wireframeList) {

        logger.info("Starting build of project {}", projectID);

        FigmaFile figmaFile = getFigmaFile(projectID, auth);

        // TODO: Store conversion, version, timestamp

        if (figmaFile == null) {
            throw new NotFoundException("Project " + projectID + " not found.");
        }

        // Start writing process
        try {
            codeGenerator.generateOutputFolder(figmaFile);
            codeGenerator.generatePackageFiles(figmaFile);
            codeGenerator.generateWireframes(figmaFile);
            codeGenerator.generateReusableComponents(figmaFile);
            codeGenerator.generateAppJSCode(figmaFile);

        } catch (IOException e) {
            logger.error("Error during code generation.");
            return false;
        }

        // Zip the folder where project's source code resides for downloads
        String projectFolder = String.format("%s/%s", outputStorageFolder, projectID);
        fileUtil.zipDirectory(projectFolder);
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
                wireframe.setComponents(processJsonComponents(projectID, auth, wireframe.getChildren()));
                page.addWireframe(wireframe);
            }
            figmaFile.addPage(page);
        }

        // Add image URL to wireframes
        List<String> wireframeIDs = new ArrayList<>();
        for (Wireframe wireframe : figmaFile.getWireframes()) {
            wireframeIDs.add(wireframe.getId());
        }
        Map<String, String> wireframeImageURLs = figmaAPIUtil.requestComponentImageURLs(projectID, auth, wireframeIDs);
        for (Wireframe wireframe : figmaFile.getWireframes()) {
            wireframe.setImageURL(wireframeImageURLs.get(wireframe.getId()));
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


    public List<FigmaComponent> processJsonComponents(String projectID, Authentication auth, JsonArray jsonComponents) {
        List<FigmaComponent> figmaComponents = new ArrayList<>();
        // Retrieve URL for individual components. For optimisation do it in batch.
        List<String> componentIDs = new ArrayList<>();
        for (JsonElement jsonChild : jsonComponents) {
            String id = jsonChild.getAsJsonObject().get("id").toString().replaceAll("\"", "");
            componentIDs.add(id);
        }
        Map<String, String> componentImageURLs = figmaAPIUtil.requestComponentImageURLs(projectID, auth, componentIDs);

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

            } else if (type.matches("GROUP|RECTANGLE") && name.matches("image|picture|icon")) {
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
                ((Group) component).setComponents(processJsonComponents(projectID, auth, ((Group) component).getChildren()));
            }

            component.setImageURL(componentImageURLs.get(component.getId()));
            // TODO: See what to do with absoluteBoundingBox
            // component.convertRelativePosition(this.absoluteBoundingBox);
            figmaComponents.add(component);
        }

        return figmaComponents;
    }

}
