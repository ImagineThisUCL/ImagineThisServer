package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.FrontendComponent.NavButton;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import com.ucl.imaginethisserver.FrontendComponent.Button;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Group extends FigmaComponent {
    JsonArray children;
    @Expose()
    String blendMode;
    private Map<String, FigmaComponent> componentMap = new HashMap<>();

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
                    componentMap.put(rectangle.getName(), rectangle);

                }
                case "TEXT" -> {
                    Text text = new Gson().fromJson(jsonChild, Text.class);
                    String imageURL = imageJson.get(text.getId()).toString();
                    text.setImageURL(imageURL);
                    componentMap.put(text.getName(), text);
                }
                case "VECTOR" -> {
                    Vector vector = new Gson().fromJson(jsonChild, Vector.class);
                    String imageURL = imageJson.get(vector.getId()).toString();
                    vector.setImageURL(imageURL);
                    componentMap.put(vector.getName(), vector);
                }
                case "GROUP" -> {
                    Group group = new Gson().fromJson(jsonChild, Group.class);
                    String imageURL = imageJson.get(group.getId()).toString();
                    group.setImageURL(imageURL);
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
        for(FigmaComponent component : this.componentMap.values()){
            if(component.getType().equals("RECTANGLE")){
                Rectangle rectangle = (Rectangle) component;
                button.setCornerRadius(rectangle.getCornerRadius());
                button.setRecFills(rectangle.getFills());
                if(rectangle.getTransitionNodeID() != null){
                    button.setTransitionNodeID(rectangle.getTransitionNodeID());
                }
            }
            else if(component.getType().equals("TEXT")){
                Text text = (Text) component;
                button.setCharacter(text.getCharacters());
                button.setStyle(text.getStyle());
                button.setTextFills(((Text) component).getFills());
                if(text.getTransitionNodeID() != null){
                    button.setTransitionNodeID(text.getTransitionNodeID());
                }
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

}
