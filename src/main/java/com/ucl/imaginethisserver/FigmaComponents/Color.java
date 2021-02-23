package com.ucl.imaginethisserver.FigmaComponents;


/**
 *  Object used to represent rgba colors passed from Figma API in Java
 *  Containing a function that converts this object into Frontend code
*/

public class Color {
    private float r;
    private float g;
    private float b;
    private float a;

    public Color(float r, float g, float b, float a){
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
