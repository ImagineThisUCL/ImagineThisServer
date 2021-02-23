package com.ucl.imaginethisserver.DAO;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;

import java.io.IOException;
import java.util.*;

/**
 *  The object Page represents a Page on the Figma side.
 *  The object contains the id of the page, name of the page and
 *  the wireframes within it listed in the variable children.
 */
public class Page {
    @Expose()
    private String id;
    @Expose()
    private String name;
    @Expose()
    private JsonArray children;
    /**
     * Function that converts the Figma Page into String
    */
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    private final Map<String, Wireframe> wireframeMap = new HashMap<>();
    private static final Map<String, Wireframe> wireframeIDMap = new HashMap<>();


    /**
     * Load all the components inside the group. Convert all json data retrieved from Figma API to corresponding Java object.
     * @throws IOException
     */
    public void loadWireframes(String projectID, String accessToken, AuthenticateType authType) throws IOException {
        List<String> IDList = new ArrayList<>();
        for (JsonElement pageChild : this.children) {
            String type = pageChild.getAsJsonObject().get("type").toString();
            if (type.equals("\"FRAME\"")) {
                String id = pageChild.getAsJsonObject().get("id").toString().replaceAll("\"","");
                IDList.add(id);
            }
        }
        if(IDList.size() >= 1) {
            JsonObject imageJson = FigmaAPIUtil.requestImageByIDList(IDList, projectID, accessToken, authType).get("images").getAsJsonObject();
            for (JsonElement pageChild : this.children) {
                String type = pageChild.getAsJsonObject().get("type").toString();
                if (type.equals("\"FRAME\"")) {
                    Wireframe wireframe = new Gson().fromJson(pageChild, Wireframe.class);
                    String imageURL = imageJson.get(wireframe.getId()).toString().replaceAll("\"", "");
                    wireframe.setImageURL(imageURL);
                    wireframeMap.put(wireframe.getName(), wireframe);
                    wireframeIDMap.put(wireframe.getId(), wireframe);
                }
            }
        }
    }

    /**
     * @return Function that returns all wireframes within the Page
    */
    public List<Wireframe> getWireframeList(){
        List<Wireframe> wireframeList = new ArrayList<>();
        for(Wireframe wireframe : wireframeMap.values()){
            wireframeList.add(wireframe);
        }
        return wireframeList;
    }

    /**
     * @return Function that returns a Wireframe according to its ID
     */
    public static Wireframe getWireframeByID(String id) {
        return wireframeIDMap.get(id);
    }

    /**
     * @return Function that returns a Wireframe according to its Name
     */
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

    /**
     * @return Function that returns the name of the Page
     */
    public String getName() {
        return name;
    }
    /**
     * Function that sets the name of the Page
     * @param name
     */
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
