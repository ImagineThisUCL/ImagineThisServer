package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.annotations.Expose;

public class Effect {

    // Type of effect as a string enum, including: INNER_SHADOW, DROP_SHADOW, LAYER_BLUR,
    @Expose()
    String type;
    @Expose()
    boolean visible;
    @Expose()
    double radius;
    @Expose()
    Color color;
    @Expose()
    String blendMode;

}
