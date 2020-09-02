package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * An array of fill paints applied to the node
 */
public class Fills {
    @Expose()
    private String blendMode;
    /**
     * Type of paint as a string enum
     * SOLID
     * GRADIENT_LINEAR
     * GRADIENT_RADIAL
     * GRADIENT_ANGULAR
     * GRADIENT_DIAMOND
     * IMAGE
     * EMOJI
     */
    @Expose()
    private String type;
    /**
     * Solid color of the paint
     */
    @Expose()
    private FigmaColor color;
    /**
     * Positions of key points along the gradient axis with the colors anchored there. Colors along the gradient are interpolated smoothly between neighboring gradient stops.
     */
    @Expose()
    private List<Location> gradientHandlePositions;
    @Expose()
    private List<GradientStop> gradientStops;
    /**
     * A reference to an image embedded in this node. To download the image using this reference, use the GET file images endpoint to retrieve the mapping from image references to image URLs
     */
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
