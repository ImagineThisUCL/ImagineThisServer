package com.ucl.imaginethisserver.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ucl.imaginethisserver.DAO.Page;

public class FigmaAPIUtil {
    public static JsonObject sendGetRequest(URL figmaAPI, String accessToken) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) figmaAPI.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Figma-Token", accessToken);
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonObject figmaTreeStructure = new Gson().fromJson(response.toString(), JsonObject.class);
            return figmaTreeStructure;
        } else {
            System.out.println("Request Failed");
            return null;
        }
    }

    public static JsonObject requestFigmaFile(String project_id, String accessToken) throws IOException {
        URL figmaFileApi = new URL("https://api.figma.com/v1/files/" + project_id);
        return sendGetRequest(figmaFileApi,accessToken);
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

    public static JsonObject requestImageByIDList(List<String> IDList, String projectID,String accessToken) throws IOException {
        StringBuilder ids = new StringBuilder();
        for (String id : IDList){
            ids.append(id).append(",");
        }
        ids.deleteCharAt(ids.length() - 1);
        URL figmaImageURL  = new URL("https://api.figma.com/v1/images/" + projectID + "?ids=" + ids);
        return sendGetRequest(figmaImageURL, accessToken);
    }

}
