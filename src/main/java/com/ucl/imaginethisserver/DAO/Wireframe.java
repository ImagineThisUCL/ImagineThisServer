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
    private ArrayList<FigmaComponent> componentList = new ArrayList<>();

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
                    rectangle.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(rectangle.getName(), rectangle);
                    componentList.add(rectangle);

                }
                case "TEXT" -> {
                    Text text = new Gson().fromJson(jsonChild, Text.class);
                    String imageURL = imageJson.get(text.getId()).toString();
                    text.setImageURL(imageURL);
                    text.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(text.getName(), text);
                    componentList.add(text);
                }
                case "VECTOR" -> {
                    Vector vector = new Gson().fromJson(jsonChild, Vector.class);
                    String imageURL = imageJson.get(vector.getId()).toString();
                    vector.setImageURL(imageURL);
                    vector.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(vector.getName(), vector);
                    componentList.add(vector);
                }
                case "GROUP" -> {
                    Group group = new Gson().fromJson(jsonChild, Group.class);
                    String imageURL = imageJson.get(group.getId()).toString();
                    group.setImageURL(imageURL);
                    group.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(group.getName(), group);
                    componentList.add(group);
                }

                default -> {
                    FigmaComponent figmaComponent = new Gson().fromJson(jsonChild, FigmaComponent.class);
                    String imageURL = imageJson.get(figmaComponent.getId()).toString();
                    figmaComponent.setImageURL(imageURL);
                    figmaComponent.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(figmaComponent.getName(), figmaComponent);
                    componentList.add(figmaComponent);
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

    public FigmaComponent getComponentById(String id) {
        return componentMap.get(id);
    }

    public Map<String, FigmaComponent> getFigmaComponentMap() {
        return this.componentMap;
    }

    public void sortComponentByY(){
        componentList.sort(new Comparator<FigmaComponent>() {
            @Override
            public int compare(FigmaComponent o1, FigmaComponent o2) {
                if (o1.getPositionY() > o2.getPositionY()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
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

    public ArrayList<FigmaComponent> getComponentList(){
        return this.componentList;
    }
}
