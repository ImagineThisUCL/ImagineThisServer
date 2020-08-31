package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.List;
/**
 *  Object that represents the Fills, no matter which component it is applied to.
 *  Contains most of the common values of the Fills type.
*/
public class Fills {
    @Expose()
    private String blendMode;
    @Expose()
    private String type;
    @Expose()
    private FigmaColor color;
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

    public FigmaColor getColor() {
        return color;
    }

    public void setColor(FigmaColor color) {
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
    private FigmaColor color;
    @Expose()
    double position;
}

