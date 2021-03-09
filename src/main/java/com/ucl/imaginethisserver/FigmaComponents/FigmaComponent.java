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
        if (absoluteBoundingBox == null) return 0;
        return absoluteBoundingBox.getHeight();
    }

    public int getWidth() {
        if (absoluteBoundingBox == null) return 0;
        return absoluteBoundingBox.getWidth();
    }

    public int getPositionX() {
        if (absoluteBoundingBox == null) return 0;
        return absoluteBoundingBox.getX();
    }

    public int getPositionY() {
        if (absoluteBoundingBox == null) return 0;
        return absoluteBoundingBox.getY();
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

    public static <T extends FigmaComponent> boolean containsComponent(List<FigmaComponent> figmaComponents, Class<T> cls) {
        for (FigmaComponent component : figmaComponents) {
            if (cls.isInstance(component)) return true;
        }
        return false;
    }

}
