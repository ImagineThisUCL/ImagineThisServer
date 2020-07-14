package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Vector extends FigmaComponent{
    @Expose()
    private String blendMode;
    @Expose()
    private List<Stroke> strokes;
    @Expose()
    private double strokeWeight;
    @Expose()
    private String strokeAlign;
    @Expose()
    private String strokeCap;
    @Expose()
    private String strokeJoin;
    @Expose()
    private List<Fills> fills;
    @Expose()
    private Effect effect;
    //Node ID of node to transition to in prototyping
    @Expose()
    private String transitionNodeID;
    //Opacity of the node
    @Expose()
    private double opacity;

    public String getBlendMode() {
        return blendMode;
    }

    public List<Stroke> getStrokes() {
        return strokes;
    }

    public double getStrokeWeight() {
        return strokeWeight;
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

    public Effect getEffect() {
        return effect;
    }

    public String getTransitionNodeID() {
        return transitionNodeID;
    }

    public double getOpacity() {
        return opacity;
    }
}
