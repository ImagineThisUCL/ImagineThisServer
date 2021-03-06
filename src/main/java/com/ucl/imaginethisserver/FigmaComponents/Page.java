package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.*;
import com.ucl.imaginethisserver.Conf.ExcludeSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 *  The object Page represents a Page on the Figma side.
 *  The object contains the id of the page, name of the page and
 *  the wireframes within it listed in the variable children.
 */
public class Page {
    private String id;
    private String name;
    private String type;
    @ExcludeSerialization
    private JsonArray children;
    private String prototypeStartNodeID;

    private List<Wireframe> wireframes = new ArrayList<>();

    private static Logger logger = LoggerFactory.getLogger(Page.class);

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() { return type; }
    public JsonArray getChildren() { return children; }
    public List<Wireframe> getWireframes() { return wireframes; }
    public String getPrototypeStartNodeID() { return prototypeStartNodeID; }

    public void addWireframe(Wireframe wireframe) { wireframes.add(wireframe); }

    /**
     * Function that converts the Figma Page into String
    */
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public List<FigmaComponent> getComponents() {
        List<FigmaComponent> figmaComponents = new ArrayList<>();
        for (Wireframe wireframe : getWireframes()) {
            figmaComponents.addAll(wireframe.getComponents());
        }
        return figmaComponents;
    }
    public List<FigmaComponent> getAllComponents() {
        List<FigmaComponent> figmaComponents = new ArrayList<>();
        for (Wireframe wireframe : getWireframes()) {
            figmaComponents.addAll(wireframe.getAllComponents());
        }
        return figmaComponents;
    }

    public void filterWireframesByName(List<String> wireframeList) {
        List<Wireframe> filteredWireframes = new ArrayList<>();
        for (Wireframe wireframe : getWireframes()) {
            // Generate only wanted wireframes
            if (!wireframeList.contains(wireframe.getName())) {
                logger.info("Filtering out wireframe {} from page {}.", wireframe.getName(), getName());
                filteredWireframes.add(wireframe);
            }
        }
        wireframes.removeAll(filteredWireframes);
    }

}
