package com.ucl.imaginethisserver.FigmaComponents;


import com.ucl.imaginethisserver.ReactComponents.ReactComponent;

/**
 *  A class that represents a Vector component passed from Figma API
 *  Storing necessary variables within.
*/
public class Vector extends FigmaComponent {

    private String strokeCap;
    private String strokeJoin;
    private Effect effect;
    // Opacity of the node
    private double opacity;


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

    @Override
    public ReactComponent convertToFrontendComponent() { return null; }

}
