package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.List;

public class Fills {
    @Expose()
    private String blendMode;
    @Expose()
    private String type;
    @Expose()
    private Color color;
    @Expose()
    private List<Location> gradientHandlePositions;
    @Expose()
    private List<GradientStop> gradientStops;
    @Expose()
    private String imageRef;

    public String toString(){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public String getBlendMode() {
        return blendMode;
    }

    public void setBlendMode(String blendMode) {
        this.blendMode = blendMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }
}

class GradientStop{
    @Expose()
    private Color color;
    @Expose()
    double position;
}

