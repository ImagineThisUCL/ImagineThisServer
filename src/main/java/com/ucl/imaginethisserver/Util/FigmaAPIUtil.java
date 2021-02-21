package com.ucl.imaginethisserver.Util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FigmaAPIUtil {

    @Value("${config.outputStorageFolder}")
    private String outputStorageFolder;

    private static final String FIGMA_API = "https://api.figma.com/v1";

    private Logger logger = LoggerFactory.getLogger(FigmaAPIUtil.class);

    /**
     * Helper method for sending GET requests to Figma API
     * @param figmaAPI
     * @return
     * @throws IOException
     */
    public JsonObject sendGetRequest(URL figmaAPI, Authentication auth) throws IOException {
        logger.info("Sending GET request to {}", figmaAPI);
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
            StringBuilder response = new StringBuilder();
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


    /**
     * Retrieves Figma file from Figma API and returns is as JsonObject
     * @param projectID
     * @param auth
     * @return
     * @throws IOException
     */
    public JsonObject requestFigmaFile(String projectID, Authentication auth) throws IOException {
        URL figmaFileApi = new URL(FIGMA_API + "/files/" + projectID);
        return sendGetRequest(figmaFileApi, auth);
    }



    /**
     * Retrieve the image of Figma components based on their components ID.
     * @param IDList A list which contains all of the ids of target Figma components.
     * @return A list of image urls in json format.
     * @throws IOException
     */
    public Map<String, String> requestComponentImageURLs(String projectID, Authentication auth, List<String> componentIDs) {
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
            return null;
        }
        try {
            imageURLs = sendGetRequest(figmaImageURL, auth);
        } catch (IOException e) {
            logger.error("Failed request to Figma API images.");
            return null;
        }

        // Process response
        imageURLs = imageURLs.getAsJsonObject("images");
        Map<String, String> result = new HashMap<>();
        for (String componentID : componentIDs) {
            result.put(componentID, imageURLs.get(componentID).getAsString());
        }
        return result;
    }


    /**
     *
     * @param componentID
     * @return
     */
    public String requestComponentImageURL(String projectID, Authentication auth, String componentID) {
        Map<String, String> result = requestComponentImageURLs(projectID, auth, Arrays.asList(componentID));
        if (result == null) return null;
        return result.get(componentID);
    }



    /**
     * Helper method for processing list of Page names. They must be lowercase,
     * since they will be implemented as React JavaScript classes that must be lowercase.
     * @param A list of unsanitized page names.
     * @return A list of page names.
     */
    public List<String> processPagesList(List<String> pageNamesRaw){
        ArrayList<String> pageNamesResult = new ArrayList<>();
        for (String pageName : pageNamesRaw) {
            pageNamesResult.add(StringUtils.capitalize(pageName));
        }
        return pageNamesResult;
    }


    /**
     * Download the image from Figma server to the local server according to the figma url.
     * @param imageUrl target image URL
     * @param folderName target download folder name
     * @return the name of downlaoded image (in 'png' format)
     * @throws IOException
     */
    public String downloadImage(String imageUrl, String folderName) throws IOException {
        File storageFile = new File(outputStorageFolder);
        storageFile.mkdir();
        File outputAppFolder = new File(outputStorageFolder + folderName);
        outputAppFolder.mkdir();
        File assetsFolder = new File(outputStorageFolder + folderName + "/assets");
        assetsFolder.mkdir();
        File imgFolder = new File(outputStorageFolder + folderName + "/assets/img");
        imgFolder.mkdir();

        URL url = new URL(imageUrl);
        BufferedImage img = ImageIO.read(url);
        String imageName = outputStorageFolder + folderName + "/assets/img/" + imageUrl + ".png";
        File file = new File(imageName);
        ImageIO.write(img, "png", file);
        return imageName;
    }


}
