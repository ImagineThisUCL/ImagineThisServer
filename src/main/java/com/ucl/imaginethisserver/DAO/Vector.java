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
    //Opacity of the node
    @Expose()
    private double opacity;
    @Expose()
    private double cornerRadius;


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

    public double getOpacity() {
        return opacity;
    }

    public List<Fills> getFills() {
        return fills;
    }

    public double getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }
}
