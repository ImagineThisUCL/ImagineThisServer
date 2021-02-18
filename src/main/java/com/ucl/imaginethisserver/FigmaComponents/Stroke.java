package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FigmaComponents.Color;

/**
 * This class represents the stroke paints applied to the node
 */
public class Stroke {
    @Expose()
    String blendMode;
    @Expose()
    String type;
    @Expose()
    private Color color;

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public Color getColor(){
        return this.color;
    }
}
