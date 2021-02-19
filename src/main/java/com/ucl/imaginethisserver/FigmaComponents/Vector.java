package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponents.FrontendComponent;

import java.util.List;
/**
 *  A class that represents a Vector component passed from Figma API
 *  Storing necessary variables within.
*/
public class Vector extends FigmaComponent {

    @Expose()
    private String strokeCap;
    @Expose()
    private String strokeJoin;
    @Expose()
    private Effect effect;
    //Opacity of the node
    @Expose()
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
    public FrontendComponent convertToFrontendComponent() { return null; };
}
