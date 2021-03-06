package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.*;
import org.springframework.util.StringUtils;
import com.ucl.imaginethisserver.Conf.ExcludeSerialization;

import java.util.*;

/**
 * A wireframe object contains the information of a Figma wireframe.
 */
public class Wireframe {
    private String id;
    private String name;
    @ExcludeSerialization
    private JsonArray children;
    private AbsoluteBoundingBox absoluteBoundingBox;
    private List<Paint> fills;
    private Color backgroundColor;
    private String imageURL;

    // Parent page, i.e. page in which this wireframe resides
    private Page page;
    @ExcludeSerialization
    private List<FigmaComponent> components;

    public String getId() {
        return id;
    }
    public String getName() { return convertToWireframeName(name); }
    public JsonArray getChildren() {
        return children;
    }
    public List<Paint> getFills() {
        return fills;
    }
    public AbsoluteBoundingBox getAbsoluteBoundingBox() { return absoluteBoundingBox; }
    public List<FigmaComponent> getComponents() {
        return components;
    }
    public Page getPage() {
        return page;
    }
    public Color getBackgroundColor() { return backgroundColor; }
    public String getImageURL() { return imageURL; }

    public void setName(String name) { this.name = name; }
    public void setPage(Page page) {
        this.page = page;
    }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
    public void setComponents(List<FigmaComponent> figmaComponents) {
        // Initialize list to be empty
        components = new ArrayList<>();
        for (FigmaComponent figmaComponent : figmaComponents) {
            components.add(figmaComponent);
        }
    }
    public void addComponent(FigmaComponent component) { components.add(component); }

    /**
     * Sort all of the Figma components in the wireframe, the component which have a smaller Y-axis value (which means the component locates at a higher position in the wireframe) is
     * in the front of the list.
     */
    public void sortComponentsByY() {
        components.sort((o1, o2) -> {
            if (o1.getPositionY() > o2.getPositionY()) return 1;
            else return -1;
        });
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public static String convertToWireframeName(String name) {
        String wireframeName = name.replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ -]","");
        // Need to capitalize wireframes because they will be converted to JavaScript React classes
        return StringUtils.capitalize(wireframeName);
    }

}
