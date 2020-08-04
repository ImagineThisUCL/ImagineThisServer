package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FigmaComponent {
    private String id;
    private String name;
    private String type;
    private AbsoluteBoundingBox absoluteBoundingBox;
    private String imageURL;
    private int height;
    private int width;
    private int positionX;
    private int positionY;
    private String align;
    private String transitionNodeID;

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public String getTransitionNodeID() {
        return transitionNodeID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public AbsoluteBoundingBox getAbsoluteBoundingBox() {
        return absoluteBoundingBox;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void convertRelativePosition(AbsoluteBoundingBox wireframeBoundingbox){
        this.height = (int)((this.absoluteBoundingBox.height / wireframeBoundingbox.height) * 100);
        this.width = (int)((this.absoluteBoundingBox.width / wireframeBoundingbox.width) * 100);

        this.positionX = (int)(((this.absoluteBoundingBox.x - wireframeBoundingbox.x) / wireframeBoundingbox.width) * 100);
        this.positionY = (int)(((this.absoluteBoundingBox.y - wireframeBoundingbox.y) / wireframeBoundingbox.height) * 100);

        if(this.positionX + this.width < 50){
            this.align = "LEFT";
        }else if(this.positionX > 50){
            this.align = "RIGHT";
        }else{
            this.align = "CENTER";
        }
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public String getAlign() {
        return align;
    }
}
