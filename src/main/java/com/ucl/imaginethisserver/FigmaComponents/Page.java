package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FigmaComponents.Wireframe;

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
    private String type;
    @Expose()
    private JsonArray children;

    private List<Wireframe> wireframes = new ArrayList<>();

    public String getId() {
        return id;
    };
    public String getName() {
        return name;
    };
    public String getType() { return type; };
    public JsonArray getChildren() { return children; };
    public List<Wireframe> getWireframes() { return wireframes; };

    public void addWireframe(Wireframe wireframe) { wireframes.add(wireframe); };

    /**
     * Function that converts the Figma Page into String
    */
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    /**
     * @return Function that returns a Wireframe according to its ID
     */
    public Wireframe getWireframeByID(String id) {
        return null;
    }


}
