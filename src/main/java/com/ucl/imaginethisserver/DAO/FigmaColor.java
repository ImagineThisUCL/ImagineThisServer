package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;

public class FigmaColor {
    @Expose()
    private float r;
    @Expose()
    private float g;
    @Expose()
    private float b;
    @Expose()
    private float a;

    public FigmaColor(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public String toString(){
       return "\"rgba("+(int)(r * 255) +","+(int)(g * 255) +","+(int)(b * 255)+","+a+")\"";
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getA() {
        return a;
    }
}
