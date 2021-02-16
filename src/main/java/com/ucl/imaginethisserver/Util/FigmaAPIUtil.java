package com.ucl.imaginethisserver.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.Component.WireframeComponent;
import com.ucl.imaginethisserver.DAO.*;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.FrontendComponent.Navigator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FigmaAPIUtil {

    private String projectID;
    private Authentication auth;
    private Logger logger = LoggerFactory.getLogger(FigmaAPIUtil.class);
    private static final String FIGMA_API = "https://api.figma.com/v1";
    // Used as optimisation for caching and performing bulk requests
    private static HashMap<String, String> componentImagesURLCache = new HashMap<>();

    public FigmaAPIUtil(String projectID, Authentication auth) {
        this.projectID = projectID;
        this.auth = auth;
    }

    public JsonObject sendGetRequest(URL figmaAPI) throws IOException {
        logger.info("Sending GET request to " + figmaAPI.toString());
        HttpURLConnection connection = (HttpURLConnection) figmaAPI.openConnection();
        connection.setRequestMethod("GET");

        // Set authentication headers
        if (auth.getType() == AuthenticationType.ORIGINAL_TOKEN) {
            connection.setRequestProperty("X-Figma-Token", auth.getAccessToken());
        } else if (auth.getType() == AuthenticationType.OAUTH2) {
            connection.setRequestProperty("Authorization", "Bearer " + auth.getAccessToken());
        }

        // Read and process response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new Gson().fromJson(response.toString(), JsonObject.class);

        } else {
            logger.error("Figma request failed");
            return null;
        }
    }

    public JsonObject requestFigmaFile() throws IOException {
        URL figmaFileApi = new URL(FIGMA_API + "/files/" + projectID);
        return sendGetRequest(figmaFileApi);
    }

    public FigmaFile getFigmaFile() {

        JsonObject rawFigmaFile;
        try {
            rawFigmaFile = requestFigmaFile();
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
                wireframe.setComponents(processJsonComponents(wireframe.getChildren()));
                page.addWireframe(wireframe);
            }
            figmaFile.addPage(page);
        }
        return figmaFile;
    }


    public List<FigmaComponent> processJsonComponents(JsonArray jsonComponents) {
        List<FigmaComponent> figmaComponents = new ArrayList<>();
        // Retrieve URL for individual components. For optimisation do it in bulk.
        List<String> componentIDs = new ArrayList<>();
        for (JsonElement jsonChild : jsonComponents) {
            String id = jsonChild.getAsJsonObject().get("id").toString().replaceAll("\"", "");
            componentIDs.add(id);
        }
        fetchComponentImageURLs(componentIDs);

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
                    ((Group) component).setComponents(processJsonComponents(((Group) component).getChildren()));
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



    /**
     * Retrieve the image of Figma components based on their components ID.
     * @param IDList A list which contains all of the ids of target Figma components.
     * @return A list of image urls in json format.
     * @throws IOException
     */
    public void fetchComponentImageURLs(List<String> componentIDs) {
        // Concatenate into a comma-separated string
        StringBuilder stringComponentIDs = new StringBuilder();
        for (String componentID : componentIDs) {
            stringComponentIDs.append(componentID).append(",");
        }
        stringComponentIDs.deleteCharAt(stringComponentIDs.length() - 1);

        // Make a request to Figma API
        JsonObject imageURLs;
        URL figmaImageURL;
        try {
            figmaImageURL  = new URL("https://api.figma.com/v1/images/" + projectID + "?ids=" + stringComponentIDs);
        } catch (MalformedURLException e) {
            logger.error("Could not form a URL properly.");
            return;
        }
        try {
            imageURLs = sendGetRequest(figmaImageURL);
        } catch (IOException e) {
            logger.error("Failed request to Figma API images.");
            return;
        }

        // Load values into cache
        for (String componentID : componentIDs) {
            componentImagesURLCache.put(componentID, imageURLs.get(componentID).getAsString());
        }
    }

    public void fetchComponentImageURL(String componentID) {
        fetchComponentImageURLs(Arrays.asList(componentID));
    }

    public String getComponentImageURL(String componentID) {
        // If cache does not hold the value, fetch it and store it into the cache
        if (!componentImagesURLCache.containsKey(componentID)) {
            fetchComponentImageURL(componentID);
        }
        return componentImagesURLCache.get(componentID);
    }

    /**
     * Helper method for processing list of Page names. They must be lowercase,
     * since they will be implemented as React JavaScript classes that must be lowercase.
     * @param A list of unsanitized page names.
     * @return A list of page names.
     */
    public static List<String> processPagesList(List<String> pageNamesRaw){
        ArrayList<String> pageNamesResult = new ArrayList<>();
        for (String pageName : pageNamesRaw) {
            pageNamesResult.add(StringUtils.capitalize(pageName));
        }
        return pageNamesResult;
    }

    /**
     * Generate the source code for all of target wireframes.
     * @param pageList The list of wireframe names the user need to generate
     * @param figmaTreeStructure the data returned by Figma API
     * @param projectID target project ID
     * @param accessToken user's personal access token
     * @param authType authentication type
     * @param folderName the output folder name
     * @throws IOException
     */
    public static void generateWireframes(
            String projectID,
            Authentication auth,
            JsonObject figmaTreeStructure,
            List<String> wireframeNames) throws IOException {

//        FrontendUtil.refreshStaticVariable(); // TODO: Do we need this?
//        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"","");
//
//        // TODO: Traverse all pages, not just the first one
//        List<Page> pageList = new ArrayList<>();//FigmaAPIUtil.extractPages(figmaTreeStructure);
//        Page firstPage = pageList.get(0);
//
//        firstPage.loadWireframes(projectID, auth);
//        CodeGenerator.generatePackageFiles(folderName);
//        for (String wireframeName : wireframeNames) {
//            logger.info("Generating wireframe: " + wireframeName);
//            Wireframe currentWireframe = firstPage.getWireframeByName(wireframeName);
//            List<FigmaComponent> figmaComponents = FigmaAPIUtil.convertJsonComponents(currentWireframe.getChildren());
//            currentWireframe.setComponents(figmaComponents);
//            currentWireframe.sortComponentsByY();
//            CodeGenerator.writeWireframeCode(currentWireframe, projectID, accessToken, authType, folderName);
//        }
//
//
//        for (String wireframeName : Navigator.NAVIGATOR_MAP.keySet()) {
//            if (!pageList.contains(wireframeName)) {
//                Navigator.NAVIGATOR_MAP.put(wireframeName, "Placeholder");
//                Navigator.hasPlaceholder = true;
//            }
//        }
//        if (WireframeComponent.IsContainNavBar()) {
//            CodeGenerator.writeAppJSCode(WireframeComponent.NAV_BAR, folderName);
//        } else if (!Navigator.NAVIGATOR_MAP.isEmpty()) {
//            CodeGenerator.writeAppJSCode(null, folderName);
//        }
//        if (NavBar.hasPlaceholder() || Navigator.hasPlaceholder) {
//            CodeGenerator.writePlaceholderCode(folderName);
//        }
//
//        //Zip the output folder to a zip file so that the user could download
//        ZipUtil.zipFile("OutputStorage/" + folderName);
    }


}
