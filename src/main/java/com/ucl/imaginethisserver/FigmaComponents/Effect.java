package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FigmaComponents.Color;

public class Effect {
    //This class represent the visual effect such as a shadow or blur

    //Type of effect as a string enum, including
    //INNER_SHADOW, DROP_SHADOW, LAYER_BLUR,
    @Expose()
    String type;
    @Expose()
    //Radius of the blur effect (applies to shadows as well)
    boolean visiable;
    @Expose()
    double radius;
    @Expose()
//  The color of the shadow
    Color color;
    //How far the shadow is projected in the x and y direction
    @Expose()
    String blendMode;

}
