package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * A wireframe object contains the information of a Figma wireframe.
 */
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
    private List<Paint> fills;
    @Expose()
    private Color backgroundColor;
    @Expose(serialize = true, deserialize = false)
    private String imageURL;

    // Parent page, i.e. page in which this wireframe resides
    private Page page;
    private List<FigmaComponent> components;

    public String getId() {
        return id;
    }
    public String getName() {
        String wireframeName = name.replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ -]","");
        // Need to capitalize wireframes because they will be converted to JavaScript React classes
        return StringUtils.capitalize(wireframeName);
    }
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

}
