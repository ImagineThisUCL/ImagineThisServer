package com.ucl.imaginethisserver.DAO;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;

import java.io.IOException;
import java.util.*;

public class Page {
    @Expose()
    private String id;
    @Expose()
    private String name;
    @Expose()
    private JsonArray children;

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    private Map<String, Wireframe> wireframeMap = new HashMap<>();
    private static Map<String, Wireframe> wireframeIDMap = new HashMap<>();

    public void loadWireframes(String projectID, String accessToken, AuthenticateType authType) throws IOException {
        List<String> IDList = new ArrayList<>();
        for (JsonElement pageChild : this.children) {
            String type = pageChild.getAsJsonObject().get("type").toString();
            if (type.equals("\"FRAME\"")) {
                String id = pageChild.getAsJsonObject().get("id").toString().replaceAll("\"","");
                IDList.add(id);
            }
        }
        JsonObject imageJson = FigmaAPIUtil.requestImageByIDList(IDList,projectID,accessToken,authType).get("images").getAsJsonObject();
        for (JsonElement pageChild : this.children) {
            String type = pageChild.getAsJsonObject().get("type").toString();
            if (type.equals("\"FRAME\"")) {
                Wireframe wireframe = new Gson().fromJson(pageChild, Wireframe.class);
                String imageURL = imageJson.get(wireframe.getId()).toString().replaceAll("\"","");
                wireframe.setImageURL(imageURL);
                wireframeMap.put(wireframe.getName(), wireframe);
                wireframeIDMap.put(wireframe.getId(),wireframe);
            }
        }
    }

    public List<Wireframe> getWireframeList(){
        List<Wireframe> wireframeList = new ArrayList<>();
        for(Wireframe wireframe : wireframeMap.values()){
            wireframeList.add(wireframe);
        }
        return wireframeList;
    }
    public static Wireframe getWireframeByID(String id) {
        return wireframeIDMap.get(id);
    }
    public Wireframe getWireframeByName(String name){
        return wireframeMap.get(name);
    }

    public Set<String> getWireFrameNames(){
        return wireframeMap.keySet();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonArray getChildren() {
        return children;
    }

    public void setChildren(JsonArray children) {
        this.children = children;
    }
}
