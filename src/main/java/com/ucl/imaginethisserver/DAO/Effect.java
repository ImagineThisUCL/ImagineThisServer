package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;

public class Effect {
    //This class represent the visual effect such as a shadow or blur

    //Type of effect as a string enum, including
    //INNER_SHADOW, DROP_SHADOW, LAYER_BLUR,
    @Expose()
    String type;
    @Expose()
    boolean visiable;
    @Expose()
    double radius;
    @Expose()
    Color color;
    //How far the shadow is projected in the x and y direction
    @Expose()
    Location offset;
    @Expose()
    String blendMode;

}
