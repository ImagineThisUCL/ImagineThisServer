package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponent.*;
import com.ucl.imaginethisserver.FrontendComponent.Button;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Group extends FigmaComponent {
    JsonArray children;
    @Expose()
    String blendMode;
    String transitionNodeID;
    private Map<String, FigmaComponent> componentMap = new HashMap<>();
    private AbsoluteBoundingBox wireframeBoundingBox;

    public Map<String, FigmaComponent> getComponentMap(){
        return this.componentMap;
    }

    public void loadComponent(String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
        List<String> IDList = new ArrayList<>();
        for (JsonElement pageChild : this.children) {
            String id = pageChild.getAsJsonObject().get("id").toString().replaceAll("\"", "");
            IDList.add(id);
        }
        JsonObject imageJson = FigmaAPIUtil.requestImageByIDList(IDList,projectID, accessToken, authenticateType).get("images").getAsJsonObject();
        for (JsonElement jsonChild : children) {
            String type = jsonChild.getAsJsonObject().get("type").toString();
            type = type.substring(1, type.length() - 1);
            switch (type) {
                case "RECTANGLE" -> {
                    Rectangle rectangle = new Gson().fromJson(jsonChild, Rectangle.class);
                    String imageURL = imageJson.get(rectangle.getId()).toString();
                    rectangle.setImageURL(imageURL);
                    rectangle.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(rectangle.getName(), rectangle);

                }
                case "TEXT" -> {
                    Text text = new Gson().fromJson(jsonChild, Text.class);
                    String imageURL = imageJson.get(text.getId()).toString();
                    text.setImageURL(imageURL);
                    text.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(text.getName(), text);
                }
                case "VECTOR" -> {
                    Vector vector = new Gson().fromJson(jsonChild, Vector.class);
                    String imageURL = imageJson.get(vector.getId()).toString();
                    vector.setImageURL(imageURL);
                    vector.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(vector.getName(), vector);
                }
                case "GROUP" -> {
                    Group group = new Gson().fromJson(jsonChild, Group.class);
                    String imageURL = imageJson.get(group.getId()).toString();
                    group.setImageURL(imageURL);
                    group.setWireframeBoundingBox(this.wireframeBoundingBox);
                    group.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(group.getName(), group);
                }

                default -> {
                    FigmaComponent figmaComponent = new Gson().fromJson(jsonChild, FigmaComponent.class);
                    String imageURL = imageJson.get(figmaComponent.getId()).toString();
                    figmaComponent.setImageURL(imageURL);
                    componentMap.put(figmaComponent.getName(), figmaComponent);
                }
            }
        }

    }

    public Button convertButton(){
        Button button = new Button();
        button.setPositionX(this.getPositionX());
        button.setPositionY(this.getPositionY());
        button.setWidth(this.getWidth());
        button.setHeight(this.getHeight());
        button.setAlign(this.getAlign());
        if(this.transitionNodeID!=null){
            button.setTransitionNodeID(this.transitionNodeID);
            String wireframeName = Page.getWireframeByID(this.transitionNodeID).getName();
            Navigator.NAVIGATOR_MAP.put(wireframeName,wireframeName);
        }
        for(FigmaComponent component : this.componentMap.values()){
            if(component.getType().equals("RECTANGLE")){
                Rectangle rectangle = (Rectangle) component;
                button.setCornerRadius(rectangle.getCornerRadius());
                button.setRecFills(rectangle.getFills());
                if(rectangle.getStrokes().size() > 0){
                    button.setBorderColor(rectangle.getStrokes().get(0).getColor());
                }
                button.setBorderWidth(rectangle.getStrokeWeight());
            }
            else if(component.getType().equals("TEXT")){
                Text text = (Text) component;
                button.setCharacter(text.getCharacters());
                button.setStyle(text.getStyle());
                button.setTextFills(((Text) component).getFills());
            }
        }
        return button;
    }

    public NavBar convertNavBar(String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
        NavBar navBar = new NavBar();
        navBar.setHeight(this.getHeight());
        navBar.setWidth(this.getWidth());
        navBar.setPositionX(this.getPositionX());
        navBar.setPositionY(this.getPositionY());
        for(FigmaComponent component : this.componentMap.values()){
            if(component.getType().equals("GROUP") && component.getName().contains("button")){
                NavButton navButton = new NavButton();
                ((Group)component).loadComponent(projectID,accessToken,authenticateType);
                for(FigmaComponent childComponent: ((Group)component).getComponentMap().values()){
                    if(childComponent.getType().equals("TEXT")){
                        navButton.setText(((Text)childComponent).getCharacters());
                    }else if(childComponent.getName().toLowerCase().contains("icon")){
                        navButton.setIconURL(childComponent.getImageURL());
                    }
                }
                NavBar.NAV_BUTTONS.add(navButton);
                NavBar.BUTTON_MAP.put(navButton.getText(), "Placeholder");
            }
        }

        return navBar;
    }

    public TextBox convertTextBox(){
        TextBox textbox = new TextBox();
        textbox.setPositionX(this.getPositionX());
        textbox.setPositionY(this.getPositionY());
        textbox.setWidth(this.getWidth());
        textbox.setHeight(this.getHeight());
        textbox.setAlign(this.getAlign());
        for(FigmaComponent component : this.componentMap.values()){
            if(component.getType().equals("RECTANGLE")){
                Rectangle rectangle = (Rectangle) component;
                textbox.setContainerFills(rectangle.getFills());
                textbox.setCornerRadius(rectangle.getCornerRadius());
            }else if(component.getType().equals("TEXT") && component.getName().toLowerCase().equals("placeholder")){
                Text text = (Text) component;
//                System.out.println(text);
                textbox.setPlaceholder(text.getCharacters());
                textbox.setStyle(text.getStyle());
                textbox.setTextFills(text.getFills());
            }
        }
        return textbox;
    }

    public Form convertForm(String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
        Form form = new Form();
        form.setHeight(this.getHeight());
        form.setWidth(this.getWidth());
        form.setPositionX(this.getPositionX());
        form.setPositionY(this.getPositionY());
        form.setAlign(this.getAlign());
        for(FigmaComponent component: this.componentMap.values()){
            if(component.getType().equals("TEXT")){
                FrontendText text = ((Text)component).convertToFrontendText();
                form.frontendComponentList.add(text);
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("textbox")){
                ((Group)component).loadComponent(projectID,accessToken,authenticateType);
                TextBox textBox = ((Group)component).convertTextBox();
                form.frontendComponentList.add(textBox);
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("button")){
                ((Group)component).loadComponent(projectID,accessToken,authenticateType);
                Button button = ((Group)component).convertButton();
                form.frontendComponentList.add(button);
            }else if((component.getType().equals("RECTANGLE") || component.getType().equals("VECTOR")) && component.getName().toLowerCase().equals("background")){
                switch (component.getType()){
                    case "RECTANGLE":
                        form.setBackgroundColor(((Rectangle)component).getFills().get(0).getColor());
                        form.setCornerRadius(((Rectangle)component).getCornerRadius());
                        break;
                    case "VECTOR":
                        form.setBackgroundColor(((Vector)component).getFills().get(0).getColor());
                        form.setCornerRadius(((Vector)component).getCornerRadius());
                        break;
                }
            }
        }
        form.sortComponentByY();
        return form;
    }

    public void setWireframeBoundingBox(AbsoluteBoundingBox wireframeBoundingBox) {
        this.wireframeBoundingBox = wireframeBoundingBox;
    }

    public Slider convertSlider(){
        Slider slider = new Slider();
        slider.setHeight(this.getHeight());
        slider.setWidth(this.getWidth());
        slider.setPositionX(this.getPositionX());
        slider.setPositionY(this.getPositionY());
        slider.setAlign(this.getAlign());
        for(FigmaComponent component : this.componentMap.values()){
            if(component.getType().equals("TEXT") && component.getName().toLowerCase().equals("cur_value")){
                int cur_value = Integer.parseInt(((Text)component).getCharacters());
                slider.setCur_value(cur_value);
            }else if(component.getType().equals("TEXT") && component.getName().toLowerCase().equals("min_value")){
                int min_value = Integer.parseInt(((Text)component).getCharacters());
                slider.setMin_value(min_value);
            }else if(component.getType().equals("TEXT") && component.getName().toLowerCase().equals("max_value")){
                int max_value = Integer.parseInt(((Text)component).getCharacters());
                slider.setMax_value(max_value);
            } else if(component.getType().equals("RECTANGLE") && component.getName().toLowerCase().equals("background")) {
                Rectangle rectangle = (Rectangle)component;
                slider.setBackgroundColor(rectangle.getFills().get(0).getColor());
                slider.setBorderRadius(rectangle.getCornerRadius());
            }
        }
        return slider;
    }

}
