package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucl.imaginethisserver.FrontendComponent.Image;
import com.ucl.imaginethisserver.FrontendComponent.Switch;
/**
 *  An Java object representing all FigmaComponent,
 *  contains most of the useful common value of all Figma Component
*/
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

    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
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
        this.height = (int)(this.absoluteBoundingBox.height);
        this.width = (int)(this.absoluteBoundingBox.width);

        this.positionX = (int)((this.absoluteBoundingBox.x - wireframeBoundingbox.x));
        this.positionY = (int)((this.absoluteBoundingBox.y - wireframeBoundingbox.y));

        if(this.positionX + this.width < wireframeBoundingbox.width / 2){
            this.align = "LEFT";
        }else if(this.positionX > wireframeBoundingbox.width / 2){
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

    /**
     *  Function used to convert a common Figma component into a Switch component on the frontend.
    */
    public Switch convertSwitch(){
        Switch aSwitch = new Switch();
        aSwitch.setPositionX(this.getPositionX());
        aSwitch.setPositionY(this.getPositionY());
        aSwitch.setWidth(this.getWidth());
        aSwitch.setHeight(this.getHeight());
        aSwitch.setAlign(this.getAlign());
        return aSwitch;
    }

    /**
     *  Function used to convert a common Figma component into an Image on the frontend.
    */
    public Image convertToImage(){
        Image image = new Image();
        image.setWidth(this.getWidth());
        image.setHeight(this.getHeight());
        image.setPositionX(this.getPositionX());
        image.setPositionY(this.getPositionY());
        image.setAlign(this.getAlign());
        image.setImageURL(this.getImageURL());
        return image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAbsoluteBoundingBox(AbsoluteBoundingBox absoluteBoundingBox) {
        this.absoluteBoundingBox = absoluteBoundingBox;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}


