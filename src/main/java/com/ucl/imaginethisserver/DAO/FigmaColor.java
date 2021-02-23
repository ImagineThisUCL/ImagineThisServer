package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;


/**
 *  Object used to represent rgba colors passed from Figma API in Java
 *  Containing a function that converts this object into Frontend code
*/
public class FigmaColor {
    @Expose()
    private final float r;
    @Expose()
    private final float g;
    @Expose()
    private final float b;
    @Expose()
    private final float a;

    public FigmaColor(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Function that converts this object into String that cam be used
     * directly on the frontend code.
    */
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
