package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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