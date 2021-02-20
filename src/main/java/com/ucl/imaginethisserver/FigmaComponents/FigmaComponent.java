package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponents.FrontendComponent;

import java.util.List;

/**
 * It is the father class for all of recognized Figma component.
 * representing all FigmaComponent and
 * contains most of the useful common value of all Figma Component
 */
abstract public class FigmaComponent {
    @Expose()
    private String id;
    @Expose()
    private String name;
    @Expose()
    private String type;
    @Expose()
    private List<Paint> strokes;
    @Expose()
    private double strokeWeight;
    @Expose()
    private String strokeAlign;
    @Expose()
    private AbsoluteBoundingBox absoluteBoundingBox;
    @Expose()
    private List<Paint> fills;
    @Expose()
    private double cornerRadius;
    @Expose()
    private String blendMode;

    private String imageURL;
    private int height;
    private int width;
    /**
     * The position of the Figma component in X axis, the value is relative to the wireframe
     */
    private int positionX;
    /**
     * The position of the Figma component in Y axis, the value is relative to the wireframe
     */
    private int positionY;
    private String align;

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public AbsoluteBoundingBox getAbsoluteBoundingBox() {
        return absoluteBoundingBox;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getBlendMode() {
        return blendMode;
    }

    public List<Paint> getFills() { return fills; };

    public Paint getFills(int index) {
        if (fills == null || fills.size() == 0) return null;
        else return fills.get(0);
    };

    public List<Paint> getStrokes() { return strokes; };

    public Paint getStrokes(int index) { return strokes.get(index); };

    public double getStrokeWeight() { return strokeWeight; };

    public double getCornerRadius() { return cornerRadius; };

    public String getStrokeAlign() {
        return strokeAlign;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * This method calculates the relative position (relative to the wireframe that the component belong to) of current Figma component,
     * and calculates the align attribute (LEFT, RIGHT, CENTER) for the current Figma component.
     * @param wireframeBoundingbox the position information of the wireframe which the current Figma component belong to.
     */
    public void convertRelativePosition(AbsoluteBoundingBox wireframeBoundingbox){
        this.height = (int)(this.absoluteBoundingBox.height);
        this.width = (int)(this.absoluteBoundingBox.width);

        this.positionX = (int)((this.absoluteBoundingBox.x - wireframeBoundingbox.x));
        this.positionY = (int)((this.absoluteBoundingBox.y - wireframeBoundingbox.y));

        if(this.positionX + this.width < wireframeBoundingbox.width / 2){
            this.align = "LEFT";
        }else if(this.positionX > wireframeBoundingbox.width / 2){
            this.align = "RIGHT";
        }else{
            this.align = "CENTER";
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public String getAlign() {
        return align;
    }

    /**
     *  Function used to convert a common Figma component into a Switch component on the frontend.
    */



    public void setType(String type) {
        this.type = type;
    }

    public void setAbsoluteBoundingBox(AbsoluteBoundingBox absoluteBoundingBox) {
        this.absoluteBoundingBox = absoluteBoundingBox;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    abstract public FrontendComponent convertToFrontendComponent();

}
