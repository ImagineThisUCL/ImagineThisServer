package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponent.*;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import com.ucl.imaginethisserver.Util.FrontendUtil;

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

    public Map<String, FigmaComponent> getComponentMap() {
        return this.componentMap;
    }

    public void loadComponent(String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
        List<String> IDList = new ArrayList<>();
        for (JsonElement pageChild : this.children) {
            String id = pageChild.getAsJsonObject().get("id").toString().replaceAll("\"", "");
            IDList.add(id);
        }
        JsonObject imageJson = FigmaAPIUtil.requestImageByIDList(IDList, projectID, accessToken, authenticateType).get("images").getAsJsonObject();
        for (JsonElement jsonChild : children) {
            String type = jsonChild.getAsJsonObject().get("type").toString();
            type = type.substring(1, type.length() - 1);
            String imageURL = "";
            switch (type) {
                case "RECTANGLE":
                    Rectangle rectangle = new Gson().fromJson(jsonChild, Rectangle.class);
                    imageURL = imageJson.get(rectangle.getId()).toString();
                    rectangle.setImageURL(imageURL);
                    rectangle.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(rectangle.getId(), rectangle);
                    break;

                case "TEXT":
                    Text text = new Gson().fromJson(jsonChild, Text.class);
                    imageURL = imageJson.get(text.getId()).toString();
                    text.setImageURL(imageURL);
                    text.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(text.getId(), text);
                    break;

                case "VECTOR":
                    Vector vector = new Gson().fromJson(jsonChild, Vector.class);
                    imageURL = imageJson.get(vector.getId()).toString();
                    vector.setImageURL(imageURL);
                    vector.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(vector.getId(), vector);
                    break;
                case "GROUP":
                case "INSTANCE":
                    Group group = new Gson().fromJson(jsonChild, Group.class);
                    imageURL = imageJson.get(group.getId()).toString();
                    group.setImageURL(imageURL);
                    group.setType("GROUP");
                    group.setWireframeBoundingBox(this.wireframeBoundingBox);
                    group.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(group.getId(), group);
                    break;
                case "ELLIPSE":
                    Ellipse ellipse = new Gson().fromJson(jsonChild, Ellipse.class);
                    imageURL = imageJson.get(ellipse.getId()).toString();
                    ellipse.setImageURL(imageURL);
                    ellipse.convertRelativePosition(this.wireframeBoundingBox);
                    componentMap.put(ellipse.getId(), ellipse);
                    break;
                default:
                    FigmaComponent figmaComponent = new Gson().fromJson(jsonChild, FigmaComponent.class);
                    imageURL = imageJson.get(figmaComponent.getId()).toString();
                    figmaComponent.setImageURL(imageURL);
                    componentMap.put(figmaComponent.getId(), figmaComponent);
                    break;
            }
        }

    }

    public Button convertButton() {
        Button button = new Button();
        button.setPositionX(this.getPositionX());
        button.setPositionY(this.getPositionY());
        button.setWidth(this.getWidth());
        button.setHeight(this.getHeight());
        button.setAlign(this.getAlign());
        if (this.transitionNodeID != null) {
            button.setTransitionNodeID(this.transitionNodeID);
            String wireframeName = Page.getWireframeByID(this.transitionNodeID).getName();
            Navigator.NAVIGATOR_MAP.put(wireframeName, wireframeName);
        }
        for (FigmaComponent component : this.componentMap.values()) {
            switch (component.getType()) {
                case "RECTANGLE" :
                    Rectangle rectangle = (Rectangle) component;
                    button.setCornerRadius(rectangle.getCornerRadius());
                    button.setRecFills(rectangle.getFills());
                    if (rectangle.getStrokes().size() > 0) {
                        button.setBorderColor(rectangle.getStrokes().get(0).getColor());
                    }
                    button.setBorderWidth(rectangle.getStrokeWeight());
                break;
                case "TEXT":
                    Text text = (Text) component;
                    button.setCharacter(text.getCharacters());
                    button.setStyle(text.getStyle());
                    button.setTextFills(((Text) component).getFills());
                break;
                case "VECTOR":
                    Vector vector = (Vector) component;
                    button.setCornerRadius(vector.getCornerRadius());
                    button.setRecFills(vector.getFills());
                    if (vector.getStrokes().size() > 0) {
                        button.setBorderColor(vector.getStrokes().get(0).getColor());
                    }
                    button.setBorderWidth(vector.getStrokeWeight());
                break;
                case "ELLIPSE":
                    Ellipse ellipse = (Ellipse) component;
                    button.setCircle(true);
                    button.setRecFills(ellipse.getFills());
                    if (ellipse.getStrokes().size() > 0) {
                        button.setBorderColor(ellipse.getStrokes().get(0).getColor());
                    }
                    button.setBorderWidth(ellipse.getStrokeWeight());
                break;
            }
        }
        return button;
    }

    public ImageButton convertImageButton(String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
        ImageButton imageButton = new ImageButton();
        imageButton.setPositionX(this.getPositionX());
        imageButton.setPositionY(this.getPositionY());
        imageButton.setWidth(this.getWidth());
        imageButton.setHeight(this.getHeight());
        imageButton.setAlign(this.getAlign());
        if (this.transitionNodeID != null) {
            imageButton.setTransitionNodeID(this.transitionNodeID);
            String wireframeName = Page.getWireframeByID(this.transitionNodeID).getName();
            Navigator.NAVIGATOR_MAP.put(wireframeName, wireframeName);
        }
        for (FigmaComponent component : this.componentMap.values()) {
            if ((component.getType().equals("GROUP") || component.getType().equals("RECTANGLE")) && (component.getName().toLowerCase().contains("image")||component.getName().toLowerCase().contains("icon")||component.getName().toLowerCase().contains("picture"))) {
                imageButton.setImageURL(component.getImageURL());
            }
        }
        return imageButton;
    }

    public NavBar convertNavBar(String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
        NavBar navBar = new NavBar();
        navBar.setHeight(this.getHeight());
        navBar.setWidth(this.getWidth());
        navBar.setPositionX(this.getPositionX());
        navBar.setPositionY(this.getPositionY());
        for (FigmaComponent component : this.componentMap.values()) {
            if (component.getType().equals("GROUP") && component.getName().contains("button")) {
                NavButton navButton = new NavButton();
                navButton.setWidth(component.getWidth());
                navButton.setHeight(component.getHeight());
                navButton.setPositionX(component.getPositionX());
                navButton.setPositionY(component.getPositionY());
                ((Group) component).loadComponent(projectID, accessToken, authenticateType);
                for (FigmaComponent childComponent : ((Group) component).getComponentMap().values()) {
                    if (childComponent.getType().equals("TEXT")) {
                        navButton.setText(((Text) childComponent).getCharacters());
                    } else if (childComponent.getName().toLowerCase().contains("image") || childComponent.getName().toLowerCase().contains("icon") || childComponent.getName().toLowerCase().contains("picture")) {
                        navButton.setIconURL(childComponent.getImageURL());
                    }
                }
                String transitionNodeID = ((Group) component).transitionNodeID;
                if(transitionNodeID != null && FrontendUtil.GENERATE_PAGE_LIST.contains(Page.getWireframeByID(transitionNodeID).getName())){
                    NavBar.BUTTON_MAP.put(navButton.getText(), Page.getWireframeByID(transitionNodeID).getName());
                }else{
                    NavBar.BUTTON_MAP.put(navButton.getText(), "Placeholder");
                }
                NavBar.NAV_BUTTONS.add(navButton);


            }
            if(component.getType().equals("RECTANGLE") && component.getName().contains("background")){
                Rectangle rectangle = (Rectangle) component;
                NavBar.containerFills = rectangle.getFills();
            }
        }
        return navBar;
    }

    public TextBox convertTextBox() {
        TextBox textbox = new TextBox();
        textbox.setPositionX(this.getPositionX());
        textbox.setPositionY(this.getPositionY());
        textbox.setWidth(this.getWidth());
        textbox.setHeight(this.getHeight());
        textbox.setAlign(this.getAlign());

        for (FigmaComponent component : this.componentMap.values()) {
            if (component.getType().equals("RECTANGLE")) {
                Rectangle rectangle = (Rectangle) component;
                textbox.setContainerFills(rectangle.getFills());
                textbox.setCornerRadius(rectangle.getCornerRadius());
            } else if (component.getType().equals("VECTOR")) {
                Vector vector = (Vector) component;
                textbox.setContainerFills(vector.getFills());
                textbox.setCornerRadius(vector.getCornerRadius());
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("placeholder")) {
                Text text = (Text) component;
                textbox.setPlaceholder(text.getCharacters());
                textbox.setStyle(text.getStyle());
                textbox.setTextFills(text.getFills());
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("label")) {
                Text text = (Text) component;
                textbox.setLabel(text.getCharacters());
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
        for (FigmaComponent component : this.componentMap.values()) {
            if (component.getType().equals("TEXT")) {
                FrontendText text = ((Text) component).convertToFrontendText();
                form.frontendComponentList.add(text);
                form.setContainText(true);
            } else if (component.getName().toLowerCase().contains("switch")) {
                Switch aSwitch = component.convertSwitch();
                form.frontendComponentList.add(aSwitch);
                form.setContainSwitch(true);
            } else if (component.getType().equals("GROUP") && component.getName().toLowerCase().contains("input")) {
                ((Group) component).loadComponent(projectID, accessToken, authenticateType);
                TextBox textBox = ((Group) component).convertTextBox();
                form.frontendComponentList.add(textBox);
                form.setContainTextBox(true);
            } else if (component.getType().equals("GROUP") && component.getName().toLowerCase().contains("textbutton")) {
                ((Group) component).loadComponent(projectID, accessToken, authenticateType);
                Button button = ((Group) component).convertButton();
                form.frontendComponentList.add(button);
                form.setContainButton(true);
            } else if (component.getType().equals("GROUP") && component.getName().toLowerCase().contains("imagebutton")) {
                ((Group) component).loadComponent(projectID, accessToken, authenticateType);
                ImageButton imageButton = ((Group) component).convertImageButton(projectID, accessToken, authenticateType);
                form.frontendComponentList.add(imageButton);
                form.setContainImageButton(true);
            } else if ((component.getType().equals("RECTANGLE") || component.getType().equals("GROUP")) && (component.getName().toLowerCase().contains("image")||component.getName().toLowerCase().contains("icon")||component.getName().toLowerCase().contains("picture"))) {
                Image image;
                if (component.getType().equals("RECTANGLE")) {
                    image = ((Rectangle) component).convertToImage();
                } else {
                    image = ((Group) component).convertToImage();
                }
                form.frontendComponentList.add(image);
                form.setContainImage(true);
            } else if (component.getType().equals("GROUP") && component.getName().toLowerCase().contains("chart")) {
                ((Group) component).loadComponent(projectID, accessToken, authenticateType);
                Chart fixedChart = ((Group) component).convertToFixedChart();
                form.frontendComponentList.add(fixedChart);
                form.setContainChart(true);
            } else if (component.getType().equals("GROUP") && component.getName().toLowerCase().contains("dropdown")) {
                ((Group) component).loadComponent(projectID, accessToken, authenticateType);
                Dropdown dropdown = ((Group) component).convertToDropdown();
                form.frontendComponentList.add(dropdown);
                form.setContainDropdown(true);
            } else if ((component.getType().equals("RECTANGLE") || component.getType().equals("VECTOR")) && component.getName().toLowerCase().equals("background")) {
                switch (component.getType()) {
                    case "RECTANGLE":
                        Rectangle rectangle = (Rectangle) component;
                        if (rectangle.getFills().size() > 0) {
                            form.setBackgroundColor(rectangle.getFills().get(0).getColor());
                        }
                        form.setCornerRadius(rectangle.getCornerRadius());
                        if (rectangle.getStrokes().size() > 0) {
                            form.setBorderColor(rectangle.getStrokes().get(0).getColor());
                        }
                        form.setBorderWidth(rectangle.getStrokeWeight());

                        break;
                    case "VECTOR":
                        Vector vector = (Vector) component;
                        if (vector.getFills().size() > 0) {
                            form.setBackgroundColor(vector.getFills().get(0).getColor());
                        }
                        form.setCornerRadius((vector.getCornerRadius()));
                        if (vector.getStrokes().size() > 0) {
                            form.setBorderColor(vector.getStrokes().get(0).getColor());
                        }
                        form.setBorderWidth(vector.getStrokeWeight());
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

    public Slider convertSlider() {
        Slider slider = new Slider();
        slider.setHeight(this.getHeight());
        slider.setWidth(this.getWidth());
        slider.setPositionX(this.getPositionX());
        slider.setPositionY(this.getPositionY());
        slider.setAlign(this.getAlign());
        for (FigmaComponent component : this.componentMap.values()) {
            if (component.getType().equals("TEXT") && component.getName().toLowerCase().equals("cur_value")) {
                int cur_value = Integer.parseInt(((Text) component).getCharacters());
                slider.setCur_value(cur_value);
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().equals("min_value")) {
                int min_value = Integer.parseInt(((Text) component).getCharacters());
                slider.setMin_value(min_value);
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().equals("max_value")) {
                int max_value = Integer.parseInt(((Text) component).getCharacters());
                slider.setMax_value(max_value);
            } else if (component.getType().equals("RECTANGLE") && component.getName().toLowerCase().equals("background")) {
                Rectangle rectangle = (Rectangle) component;
                slider.setBackgroundColor(rectangle.getFills().get(0).getColor());
                slider.setBorderRadius(rectangle.getCornerRadius());
            }
        }
        return slider;
    }

    public Chart convertToFixedChart() {
        Chart fixedChart = new Chart();
        fixedChart.setHeight(this.getHeight());
        fixedChart.setWidth(this.getWidth());
        fixedChart.setPositionX(this.getPositionX());
        fixedChart.setPositionY(this.getPositionY());
        fixedChart.setAlign(this.getAlign());

        return fixedChart;
    }

    public Dropdown convertToDropdown() {
        Dropdown dropdown = new Dropdown();
        dropdown.setHeight(this.getHeight());
        dropdown.setWidth(this.getWidth());
        dropdown.setPositionX(this.getPositionX());
        dropdown.setPositionY(this.getPositionY());
        dropdown.setAlign(this.getAlign());

        for (FigmaComponent component : this.componentMap.values()) {
            if (component.getType().equals("RECTANGLE")) {
                Rectangle rectangle = (Rectangle) component;
                dropdown.setContainerFills(rectangle.getFills());
                dropdown.setCornerRadius(rectangle.getCornerRadius());
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("option")) {
                Text text = (Text) component;
                dropdown.setOption(text.getCharacters());
                dropdown.setStyle(text.getStyle());
                dropdown.setTextFills(text.getFills());
            }
        }

        return dropdown;
    }

    public Image convertToImage() {
        Image image = new Image();
        image.setWidth(this.getWidth());
        image.setHeight(this.getHeight());
        image.setPositionX(this.getPositionX());
        image.setPositionY(this.getPositionY());
        image.setImageURL(this.getImageURL());
        return image;
    }

}
