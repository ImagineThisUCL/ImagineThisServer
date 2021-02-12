package com.ucl.imaginethisserver.DAO;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * A wireframe object contains the information of a Figma wireframe.
 */
public class Wireframe {
    @Expose()
    private String id;
    @Expose()
    private String name;
    /**
     * The child node of the wireframe in Json data format
     */
    @Expose(serialize = false)
    private JsonArray children;
    @Expose()
    private AbsoluteBoundingBox absoluteBoundingBox;
    @Expose()
    private List<Fills> fills;
    @Expose()
    private String imageURL;
    /**
     * The key is the id of the figma component
     * The value is the corresponding FigmaComponent object
     */
    private Map<String, FigmaComponent> componentMap = new HashMap<>();
    private ArrayList<FigmaComponent> componentList = new ArrayList<>();

    public void loadComponent(String projectID, String accessToken, AuthenticateType authType) throws IOException {
        List<String> IDList = new ArrayList<>();
        for (JsonElement pageChild : this.children) {
            String id = pageChild.getAsJsonObject().get("id").toString().replaceAll("\"", "");
            IDList.add(id);
        }
        JsonObject imageJson = FigmaAPIUtil.requestImageByIDList(IDList, projectID, accessToken, authType).get("images").getAsJsonObject();
        for (JsonElement jsonChild : children) {
            String type = jsonChild.getAsJsonObject().get("type").toString();
            type = type.substring(1, type.length() - 1);
            String imageURL = "";
            switch (type) {
                case "RECTANGLE":
                    Rectangle rectangle = new Gson().fromJson(jsonChild, Rectangle.class);
                    imageURL = imageJson.get(rectangle.getId()).toString();
                    rectangle.setImageURL(imageURL);
                    rectangle.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(rectangle.getId(), rectangle);
                    componentList.add(rectangle);
                    break;
                case "TEXT":
                    Text text = new Gson().fromJson(jsonChild, Text.class);
                    imageURL = imageJson.get(text.getId()).toString();
                    text.setImageURL(imageURL);
                    text.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(text.getId(), text);
                    componentList.add(text);
                    break;
                case "VECTOR":
                    Vector vector = new Gson().fromJson(jsonChild, Vector.class);
                    imageURL = imageJson.get(vector.getId()).toString();
                    vector.setImageURL(imageURL);
                    vector.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(vector.getId(), vector);
                    componentList.add(vector);
                    break;
                case "GROUP":
                case "INSTANCE":
                    Group group = new Gson().fromJson(jsonChild, Group.class);
                    group.setType("GROUP");
                    imageURL = imageJson.get(group.getId()).toString();
                    group.setImageURL(imageURL);
                    group.setWireframeBoundingBox(this.absoluteBoundingBox);
                    group.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(group.getId(), group);
                    componentList.add(group);
                    break;
                case "ELLIPSE":
                    Ellipse ellipse = new Gson().fromJson(jsonChild, Ellipse.class);
                    imageURL = imageJson.get(ellipse.getId()).toString();
                    ellipse.setImageURL(imageURL);
                    ellipse.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(ellipse.getId(), ellipse);
                    componentList.add(ellipse);
                    break;

                default:
                    FigmaComponent figmaComponent = new Gson().fromJson(jsonChild, FigmaComponent.class);
                    imageURL = imageJson.get(figmaComponent.getId()).toString();
                    figmaComponent.setImageURL(imageURL);
                    figmaComponent.convertRelativePosition(this.absoluteBoundingBox);
                    componentMap.put(figmaComponent.getId(), figmaComponent);
                    componentList.add(figmaComponent);
                    break;
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

    /**
     * Sort all of the Figma components in the wireframe, the component which have a smaller Y-axis value (which means the component locates at a higher position in the wireframe) is
     * in the front of the list.
     */
    public void sortComponentByY() {
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
        // Need to capitalize wireframes because they will be converted to JavaScript React classes
        return StringUtils.capitalize(name);
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

    public ArrayList<FigmaComponent> getComponentList() {
        return this.componentList;
    }
}
