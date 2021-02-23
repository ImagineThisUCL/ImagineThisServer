package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucl.imaginethisserver.FrontendComponents.FrontendComponent;

import java.util.List;

/**
 * It is the father class for all of recognized Figma component.
 * representing all FigmaComponent and
 * contains most of the useful common value of all Figma Component
 */
abstract public class FigmaComponent {
    private String id;
    private String name;
    private String type;
    private List<Paint> strokes;
    private double strokeWeight;
    private String strokeAlign;
    private AbsoluteBoundingBox absoluteBoundingBox;
    private List<Paint> fills;
    private double cornerRadius;
    private String blendMode;

    private int height;
    private int width;
    private int positionX;
    private int positionY;
    private String imageURL;
    private FigmaFile figmaFile; // Give each component access to the whole file
    private String align;

    public abstract FrontendComponent convertToFrontendComponent();

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

    public List<Paint> getFills() { return fills; }

    public Paint getFills(int index) {
        if (fills == null || fills.isEmpty()) return null;
        else return fills.get(0);
    }

    public List<Paint> getStrokes() { return strokes; }

    public Paint getStrokes(int index) { return strokes.get(index); }

    public double getStrokeWeight() { return strokeWeight; }

    public double getCornerRadius() { return cornerRadius; }

    public String getStrokeAlign() {
        return strokeAlign;
    }

    public FigmaFile getFigmaFile() { return figmaFile; }

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


    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setFigmaFile(FigmaFile figmaFile) { this.figmaFile = figmaFile; }

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


    /**
     * This method calculates the relative position (relative to the wireframe that the component belong to) of current Figma component,
     * and calculates the align attribute (LEFT, RIGHT, CENTER) for the current Figma component.
     * @param wireframeBoundingbox the position information of the wireframe which the current Figma component belong to.
     */
    public void convertRelativePosition(AbsoluteBoundingBox wireframeBoundingbox) {
        height = (int) (absoluteBoundingBox.getHeight());
        width = (int) (absoluteBoundingBox.getWidth());

        positionX = (int) (absoluteBoundingBox.getX() - wireframeBoundingbox.getX());
        positionY = (int) (absoluteBoundingBox.getY() - wireframeBoundingbox.getY());

        if (this.positionX + this.width < wireframeBoundingbox.getWidth() / 2) {
            align = "LEFT";
        } else if (this.positionX > wireframeBoundingbox.getWidth() / 2){
            align = "RIGHT";
        } else {
            align = "CENTER";
        }
    }

    public static <T extends FigmaComponent> boolean containsComponent(List<FigmaComponent> figmaComponents, Class<T> cls) {
        for (FigmaComponent component : figmaComponents) {
            if (cls.isInstance(component)) return true;
        }
        return false;
    }

}
