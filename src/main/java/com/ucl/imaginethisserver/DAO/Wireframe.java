package com.ucl.imaginethisserver.DAO;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;

import java.io.IOException;
import java.util.*;

public class Wireframe {
    @Expose()
    private String id;
    @Expose()
    private String name;
    @Expose(serialize = false)
    private JsonArray children;
    @Expose()
    private AbsoluteBoundingBox absoluteBoundingBox;
    @Expose()
    private List<Fills> fills;
    @Expose()
    private String imageURL;
    private Map<String, FigmaComponent> componentMap = new HashMap<>();
    private Map<String, String> IDNameMap = new HashMap<>();

    public void loadComponent(String projectID, String accessToken, AuthenticateType authType) throws IOException {
        List<String> IDList = new ArrayList<>();
        for (JsonElement pageChild : this.children) {
            String id = pageChild.getAsJsonObject().get("id").toString().replaceAll("\"", "");
            IDList.add(id);
        }
        JsonObject imageJson = FigmaAPIUtil.requestImageByIDList(IDList,projectID,accessToken, authType).get("images").getAsJsonObject();
        for (JsonElement jsonChild : children) {
            String type = jsonChild.getAsJsonObject().get("type").toString();
            type = type.substring(1, type.length() - 1);
            switch (type) {
                case "RECTANGLE" -> {
                    Rectangle rectangle = new Gson().fromJson(jsonChild, Rectangle.class);
                    String imageURL = imageJson.get(rectangle.getId()).toString();
                    rectangle.setImageURL(imageURL);
                    componentMap.put(rectangle.getName(), rectangle);

                }
                case "TEXT" -> {
                    Text text = new Gson().fromJson(jsonChild, Text.class);
                    String imageURL = imageJson.get(text.getId()).toString();
                    text.setImageURL(imageURL);
                    componentMap.put(text.getName(), text);
                }
                case "VECTOR" -> {
                    Vector vector = new Gson().fromJson(jsonChild, Vector.class);
                    String imageURL = imageJson.get(vector.getId()).toString();
                    vector.setImageURL(imageURL);
                    componentMap.put(vector.getName(), vector);
                }
                case "GROUP" -> {
                    Group group = new Gson().fromJson(jsonChild, Group.class);
                    String imageURL = imageJson.get(group.getId()).toString();
                    group.setImageURL(imageURL);
                    componentMap.put(group.getName(), group);
                }

                default -> {
                    FigmaComponent figmaComponent = new Gson().fromJson(jsonChild, FigmaComponent.class);
                    String imageURL = imageJson.get(figmaComponent.getId()).toString();
                    figmaComponent.setImageURL(imageURL);
                    componentMap.put(figmaComponent.getName(), figmaComponent);
                }
            }
        }
    }

    public Set<String> getComponentNameSet() {
        return componentMap.keySet();
    }

    public int getComponentListSize() {
        return this.componentMap.size();
    }

    public FigmaComponent getComponentByName(String name) {
        return componentMap.get(name);
    }

    public Map<String, FigmaComponent> getFigmaComponentMap() {
        return this.componentMap;
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public JsonArray getChildren() {
        return children;
    }

    public List<Fills> getFills() {
        return fills;
    }

    public AbsoluteBoundingBox getAbsoluteBoundingBox() {
        return absoluteBoundingBox;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
