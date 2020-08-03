package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Stroke {
    @Expose()
    String blendMode;
    @Expose()
    String type;
    @Expose()
    private FigmaColor color;

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public FigmaColor getColor(){
        return this.color;
    }
}
