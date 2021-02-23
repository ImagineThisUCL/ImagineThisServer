package com.ucl.imaginethisserver.FigmaComponents;


/**
 * The class used to store the positioning information of a Figma component, it is contained in every figma component, including 4 fields
 * - width: the width of the figma component
 * - height the height of the figma component
 * - x: the coordinate of the component in x-axis [relative to the whole page]
 * - y: the coordinate of the component in y-axis [relative to the whole page]
 */
public class AbsoluteBoundingBox {
    private double width;
    private double height;
    private double x;
    private double y;

    public AbsoluteBoundingBox(double width, double height, double x, double y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public double getHeight() { return height; }

    public double getWidth() { return width; }

    public double getX() { return x; }

    public double getY() { return y; }

}
