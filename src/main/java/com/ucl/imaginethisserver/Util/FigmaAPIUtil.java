package com.ucl.imaginethisserver.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.Component.WireframeComponent;
import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.DAO.Group;
import com.ucl.imaginethisserver.DAO.Page;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.FrontendComponent.Navigator;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FigmaAPIUtil {
    /**
     * Send a GET request to an figma API
     * @param figmaAPI The Figma API url address
     * @param accessToken the user's personal access token
     * @param authType The type of access token
     * @return the Json format data returned by the Figma.
     * @throws IOException
     */
    public static JsonObject sendGetRequest(URL figmaAPI, String accessToken,AuthenticateType authType) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) figmaAPI.openConnection();
        connection.setRequestMethod("GET");
        if(authType == AuthenticateType.ORIGINAL_TOKEN){
            connection.setRequestProperty("X-Figma-Token", accessToken);
        }else if(authType == AuthenticateType.OAUTH2){
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        }

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
            System.out.println("Request Failed");
            return null;
        }
    }

    /**
     * Send the GET request to call Figma API to access the information of target Figma project.
     * @param project_id The target project ID
     * @param accessToken user's personal access token
     * @param authType authentication type
     * @return
     * @throws IOException
     */
    public static JsonObject requestFigmaFile(String project_id, String accessToken,AuthenticateType authType) throws IOException {
        URL figmaFileApi = new URL("https://api.figma.com/v1/files/" + project_id);
        return sendGetRequest(figmaFileApi,accessToken,authType);
    }



    public static List<Page> extractPages(JsonObject figmaTreeStructure){
        List<Page> pageList = new ArrayList<>();
        if (figmaTreeStructure != null) {
            JsonArray pages = figmaTreeStructure.get("document").getAsJsonObject().get("children").getAsJsonArray(); //Get pages
            for (JsonElement pageJson : pages){
                Page page = new Gson().fromJson(pageJson, Page.class);
                pageList.add(page);
            }
        }
        return pageList;
    }

    /**
     * Retrieve the image of Figma components based on their components ID.
     * @param IDList A list which contains all of the ids of target Figma components.
     * @return A list of image urls in json format.
     * @throws IOException
     */
    public static JsonObject requestImageByIDList(List<String> IDList, String projectID,String accessToken, AuthenticateType authType) throws IOException {
        StringBuilder ids = new StringBuilder();
        for (String id : IDList){
            ids.append(id).append(",");
        }
        ids.deleteCharAt(ids.length() - 1);
        URL figmaImageURL  = new URL("https://api.figma.com/v1/images/" + projectID + "?ids=" + ids);
        return sendGetRequest(figmaImageURL, accessToken, authType);
    }

    public static List<String> convertJsonToList(String jsonString){
        jsonString = jsonString.substring(1,jsonString.length()-1);
        String[] array = jsonString.split(", ");
        return new ArrayList<>(Arrays.asList(array));
    }

    /**
     * Generate the source code for all of target wireframes.
     * @param names The list of wireframe names the user need to generate
     * @param figmaTreeStructure the data returned by Figma API
     * @param projectID target project ID
     * @param accessToken user's personal access token
     * @param authType authentication type
     * @param folderName the output folder name
     * @throws IOException
     */
    public static void generatePageByName(List<String> names, JsonObject figmaTreeStructure, String projectID, String accessToken, AuthenticateType authType, String folderName) throws IOException {
        FrontendUtil.refreshStaticVariable();
        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"","");
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);

        testPage.loadWireframes(projectID, accessToken, authType);
        CodeGenerator.generatePackageFiles(folderName, projectName);
        for(String name : names){
            System.out.println("Now Generating: " + name);
            Wireframe setUpWireframe = testPage.getWireframeByName(name);
            setUpWireframe.loadComponent(projectID,accessToken,authType);
            setUpWireframe.sortComponentByY();
            for(FigmaComponent component : setUpWireframe.getComponentList()){
                if(component.getType().equals("GROUP")){
                    ((Group)component).loadComponent(projectID,accessToken,authType);
                }
            }
            CodeGenerator.writeWireframeCode(setUpWireframe.getName(),setUpWireframe, projectID, accessToken, authType, folderName);
        }
        for(String wireframeName : Navigator.NAVIGATOR_MAP.keySet()){
            if(!names.contains(wireframeName)){
                Navigator.NAVIGATOR_MAP.put(wireframeName, "Placeholder");
                Navigator.hasPlaceholder = true;
            }
        }
        if(WireframeComponent.IsContainNavBar()){
            CodeGenerator.writeAppJSCode(WireframeComponent.NAV_BAR, folderName);
        }else if(!Navigator.NAVIGATOR_MAP.isEmpty()){
            CodeGenerator.writeAppJSCode(null, folderName);
        }
        if(NavBar.hasPlaceholder() || Navigator.hasPlaceholder){
            CodeGenerator.writePlaceholderCode(folderName);
        }
        //Zip the output folder to a zip file so that the user could download

        ZipUtil.zipFile("OutputStorage/" + folderName);
        FileUtils.deleteDirectory(new File("OutputStorage/" + folderName));
    }


}
