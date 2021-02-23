package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.RectangleComponent;

import java.util.List;

/**
 *  A object used to store data about this specific component passed from Figma API,
 *  basically represents a rectangle shape, which is A Type called 'Rectangle' on Figma side.
 *  The object is used to store all usable styles:
 *  fills(color or background), corner radius(cornerRadius) etc.
 */
public class Rectangle extends FigmaComponent {
    private List<Double> rectangleCornerRadii;
    private Effect effect;
    private String strokeCap;
    private String strokeJoin;
    //Opacity of the node
    private double opacity;
    
    public Effect getEffect() {
        return effect;
    }

    public List<Double> getRectangleCornerRadii() {
        return rectangleCornerRadii;
    }


    public double getOpacity() {
        return opacity;
    }


    public String getStrokeCap() {
        return strokeCap;
    }

    public String getStrokeJoin() {
        return strokeJoin;
    }


    @Override
    public RectangleComponent convertToFrontendComponent() {
        return new RectangleComponent();
    }
}
