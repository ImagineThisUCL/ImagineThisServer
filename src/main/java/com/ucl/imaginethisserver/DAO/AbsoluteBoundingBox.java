package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;
/**
 *  Object that defines the size and the positioning of a visual object within the Figma file
 */
public class AbsoluteBoundingBox {
    @Expose()
    public double width;
    @Expose()
    public double height;
    @Expose()
    public double x;
    @Expose()
    public double y;

    public AbsoluteBoundingBox(double width, double height, double x, double y){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
}
