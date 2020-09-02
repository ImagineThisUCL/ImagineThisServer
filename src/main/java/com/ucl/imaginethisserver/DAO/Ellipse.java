package com.ucl.imaginethisserver.DAO;

import java.util.List;

/**
 *  A object used to store data about this specific component passed from Figma API,
 *  basically represents a circle shape, which is A Type called 'Ellipse' on Figma side.
 *  The object is used to store all usable styles:
 *  Fills(color or background), Strokes(boundary/border) etc.
 */
public class Ellipse extends FigmaComponent{
    private List<Fills> fills;
    private List<Stroke> strokes;
    private double strokeWeight;
    private String strokeAlign;

    public List<Fills> getFills() {
        return fills;
    }

    public void setFills(List<Fills> fills) {
        this.fills = fills;
    }

    public List<Stroke> getStrokes() {
        return strokes;
    }

    public void setStrokes(List<Stroke> strokes) {
        this.strokes = strokes;
    }

    public double getStrokeWeight() {
        return strokeWeight;
    }

    public void setStrokeWeight(double strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public String getStrokeAlign() {
        return strokeAlign;
    }

    public void setStrokeAlign(String strokeAlign) {
        this.strokeAlign = strokeAlign;
    }
}
