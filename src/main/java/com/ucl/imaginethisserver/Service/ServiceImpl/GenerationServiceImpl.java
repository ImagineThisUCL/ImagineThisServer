package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.DAO.*;
import com.ucl.imaginethisserver.Service.GenerationService;
import com.ucl.imaginethisserver.Util.Authentication;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GenerationServiceImpl implements GenerationService {

    private final FigmaAPIUtil figmaAPIUtil;
    private final Logger logger = LoggerFactory.getLogger(GenerationServiceImpl.class);

    @Value("config.outputStorageFolder")
    private String outputStorageFolder;

    @Autowired
    public GenerationServiceImpl(FigmaAPIUtil figmaAPIUtil) {
        this.figmaAPIUtil = figmaAPIUtil;
    }


    /**
     *
     * @param projectID
     * @param auth
     * @param wireframeList
     */
    public void buildProject(String projectID, Authentication auth, List<String> wireframeList) {

        logger.info("Starting build of project " + projectID);

        FigmaFile figmaFile = getFigmaFile(projectID, auth);

        // TODO: Store conversion, version, timestamp

        if (figmaFile == null) {
            throw new NotFoundException("Project " + projectID + " not found.");
        }

        CodeGenerator codeGenerator = new CodeGenerator(figmaFile);

        // Start writing process
//        codeGenerator.generateOutputFolder();

        return;

    };

    public java.io.File downloadProject(String projectID) throws NotFoundException {
        return new java.io.File("asdf");
    };


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
        } catch (IOException e) { return null; };

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
        return figmaFile;
    }


    private List<FigmaComponent> processJsonComponents(String projectID, Authentication auth, JsonArray jsonComponents) {
        List<FigmaComponent> figmaComponents = new ArrayList<>();
        // Retrieve URL for individual components. For optimisation do it in bulk.
        List<String> componentIDs = new ArrayList<>();
        for (JsonElement jsonChild : jsonComponents) {
            String id = jsonChild.getAsJsonObject().get("id").toString().replaceAll("\"", "");
            componentIDs.add(id);
        }
        Map<String, String> componentImageURLs = figmaAPIUtil.requestComponentImageURLs(projectID, auth, componentIDs);

        // Process all children
        for (JsonElement jsonChild : jsonComponents) {
            String type = jsonChild.getAsJsonObject().get("type").getAsString();
            FigmaComponent component;
            switch (type) {
                case "RECTANGLE":
                    component = new Gson().fromJson(jsonChild, Rectangle.class);
                    break;
                case "TEXT":
                    component = new Gson().fromJson(jsonChild, Text.class);
                    break;
                case "VECTOR":
                    component = new Gson().fromJson(jsonChild, Vector.class);
                    break;
                case "ELLIPSE":
                    component = new Gson().fromJson(jsonChild, Ellipse.class);
                    break;
                case "GROUP":
                case "INSTANCE":
                    component = new Gson().fromJson(jsonChild, Group.class);
                    // TODO: See what to do with absoluteBoundingBox
                    // ((Group) component).setWireframeBoundingBox(this.absoluteBoundingBox);
                    // Recursively call this function
                    ((Group) component).setComponents(processJsonComponents(projectID, auth, ((Group) component).getChildren()));
                    break;
                default:
                    component = new Gson().fromJson(jsonChild, FigmaComponent.class);
            }
            component.setImageURL(getComponentImageURL(component.getId()));
            // TODO: See what to do with absoluteBoundingBox
            // component.convertRelativePosition(this.absoluteBoundingBox);
            figmaComponents.add(component);
        }
        return figmaComponents;
    };

    private String getComponentImageURL(String componentID) {
        return null;
//        // If cache does not hold the value, fetch it and store it into the cache
//        if (!componentImagesURLCache.containsKey(componentID)) {
//            fetchComponentImageURL(componentID);
//        }
//        return componentImagesURLCache.get(componentID);
    }


}
