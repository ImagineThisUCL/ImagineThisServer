package com.ucl.imaginethisserver.DAO;

import java.util.List;

public class Rectangle extends FigmaComponent {
    private List<Fills> fills;
    private double cornerRadius;
    private List<Double> rectangleCornerRadii;
    private Effect effect;
    //Node ID of node to transition to in prototyping
    private String transitionNodeID;
    private List<Stroke> strokes;
    private double strokeWeight;
    private String strokeAlign;
    private String strokeCap;
    private String strokeJoin;
    //Opacity of the node
    private double opacity;

    public double getCornerRadius() {
        return cornerRadius;
    }

    public Effect getEffect() {
        return effect;
    }

    public List<Double> getRectangleCornerRadii() {
        return rectangleCornerRadii;
    }

    public String getTransitionNodeID() {
        return transitionNodeID;
    }

    public double getStrokeWeight() {
        return strokeWeight;
    }

    public double getOpacity() {
        return opacity;
    }

    public List<Fills> getFills() {
        return fills;
    }

    public String getStrokeAlign() {
        return strokeAlign;
    }

    public String getStrokeCap() {
        return strokeCap;
    }

    public String getStrokeJoin() {
        return strokeJoin;
    }
}
