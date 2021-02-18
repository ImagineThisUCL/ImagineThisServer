package com.ucl.imaginethisserver.DAO;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponents.*;
import com.ucl.imaginethisserver.Util.Authentication;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  The object Group represents the Group Type on the Figma side.
 *  The object contains children component of the Group,
 *  the transitionNodeID that it links to (if there is any), and other values necessary.
*/
public class Group extends FigmaComponent {
    JsonArray children;
    @Expose()
    String blendMode;
    String transitionNodeID;
    private Map<String, FigmaComponent> componentMap = new HashMap<>();
    private ArrayList<FigmaComponent> componentList = new ArrayList<>();

    private AbsoluteBoundingBox wireframeBoundingBox;

    public Map<String, FigmaComponent> getComponentMap() {
        return componentMap;
    }

    public List<FigmaComponent> getComponentList() {
        return componentList;
    }

    public void setComponents(List<FigmaComponent> components) {
        // Set objects to be empty
        componentList = new ArrayList<>();
        componentMap = new HashMap<>();
        for (FigmaComponent component : components) {
            componentList.add(component);
            componentMap.put(component.getId(), component);
        }
    }

    public JsonArray getChildren() {
        return children;
    }

    /**
     * @return When a Figma group is recognized as a Button, then convert it to the Button object.
     * Deals with its transitionNodeID, basic shape or button view and
     * the text contains within and etc.
     */
    public Button convertButton() {
        Button button = new Button();
        button.setPositionX(this.getPositionX());
        button.setPositionY(this.getPositionY());
        button.setWidth(this.getWidth());
        button.setHeight(this.getHeight());
        button.setAlign(this.getAlign());
        if (this.transitionNodeID != null) {
            button.setTransitionNodeID(this.transitionNodeID);
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

    /**
     *  @return Function used to convert a Group component into a Image Button component,
     *  by dealing with its transitionNodeID, image within that function as the button view and
     *  the text contains within and etc.
     */
    public ImageButton convertImageButton(String projectID, Authentication auth) throws IOException {
        ImageButton imageButton = new ImageButton();
        imageButton.setPositionX(this.getPositionX());
        imageButton.setPositionY(this.getPositionY());
        imageButton.setWidth(this.getWidth());
        imageButton.setHeight(this.getHeight());
        imageButton.setAlign(this.getAlign());
        if (this.transitionNodeID != null) {
            imageButton.setTransitionNodeID(this.transitionNodeID);
        }
        for (FigmaComponent component : this.componentMap.values()) {
            if ((component.getType().equals("GROUP") || component.getType().equals("RECTANGLE")) && (component.getName().toLowerCase().contains("image")||component.getName().toLowerCase().contains("icon")||component.getName().toLowerCase().contains("picture"))) {
                imageButton.setImageURL(component.getImageURL());
            }
        }
        return imageButton;
    }

    /**
     *  @return Function used to convert a Group component into the bottom navigation bar,
     *  by dealing with its icon button and the background of the bar.
     */
    public NavBar convertNavBar(
            String projectID,
            Authentication auth,
            Page page) throws IOException {

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
                for (FigmaComponent childComponent : ((Group) component).getComponentMap().values()) {
                    if (childComponent.getType().equals("TEXT")) {
                        navButton.setText(((Text) childComponent).getCharacters());
                    } else if (childComponent.getName().toLowerCase().contains("image") || childComponent.getName().toLowerCase().contains("icon") || childComponent.getName().toLowerCase().contains("picture")) {
                        navButton.setIconURL(childComponent.getImageURL());
                    }
                }
                String transitionNodeID = ((Group) component).transitionNodeID;
                if (transitionNodeID != null && FrontendUtil.GENERATE_PAGE_LIST.contains(page.getWireframeByID(transitionNodeID).getName())) {
                    NavBar.BUTTON_MAP.put(navButton.getText(), page.getWireframeByID(transitionNodeID).getName());
                } else {
                    NavBar.BUTTON_MAP.put(navButton.getText(), "Placeholder");
                }
                NavBar.NAV_BUTTONS.add(navButton);


            }
            if (component.getType().equals("RECTANGLE") && component.getName().contains("background")) {
                Rectangle rectangle = (Rectangle) component;
                NavBar.containerFills = rectangle.getFills();
            }
        }
        return navBar;
    }

    /**
     *  @return Function used to convert a Group component into a input field,
     *  components like input label, placeholder of the input box etc.
     *  will be handled according to the design Guideline.
     */
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
                textbox.setLabelFills(text.getFills());
            }

        }
        return textbox;
    }

    /**
     *  @return Function used to convert a Group component into a Form,
     *  it's like a group type on the frontend side.
     *  Having related frontend components attached together into a form
     *  by wrapping them into a <View> tag.
     *
     *  In order to make the form recognize the component,
     *  the Figma components need to be converted within the form,
     *  all booleans along with the related variables of the component
     *  should be converted within the form.
     */
    public Form convertForm(String projectID, Authentication auth) throws IOException {
        Form form = new Form();
        form.setHeight(this.getHeight());
        form.setWidth(this.getWidth());
        form.setPositionX(this.getPositionX());
        form.setPositionY(this.getPositionY());
        form.setAlign(this.getAlign());
        for (FigmaComponent component : this.componentMap.values()) {
            String componentType = component.getType();
            String componentName = component.getName().toLowerCase();
            if (componentType.equals("TEXT")) {
                FrontendText text = ((Text) component).convertToFrontendText();
                form.frontendComponentList.add(text);
                form.setContainText(true);
            } else if (componentName.contains("switch")) {
                Switch aSwitch = component.convertSwitch();
                form.frontendComponentList.add(aSwitch);
                form.setContainSwitch(true);
            } else if (componentType.equals("GROUP") && componentName.contains("input")) {
                TextBox textBox = ((Group) component).convertTextBox();
                form.frontendComponentList.add(textBox);
                form.setContainTextBox(true);
            } else if (componentType.equals("GROUP") && componentName.contains("textbutton")) {
                Button button = ((Group) component).convertButton();
                form.frontendComponentList.add(button);
                form.setContainButton(true);
            } else if (componentType.equals("GROUP") && componentName.contains("imagebutton")) {
                ImageButton imageButton = ((Group) component).convertImageButton(projectID, auth);
                form.frontendComponentList.add(imageButton);
                form.setContainImageButton(true);
            } else if ((componentType.equals("RECTANGLE") || componentType.equals("GROUP")) && (componentName.contains("image") || componentName.contains("icon") || componentName.contains("picture"))) {
                Image image = component.convertToImage();
                form.frontendComponentList.add(image);
                form.setContainImage(true);
            } else if (componentType.equals("GROUP") && componentName.contains("chart")) {
                Chart fixedChart = ((Group) component).convertToFixedChart();
                form.frontendComponentList.add(fixedChart);
                form.setContainChart(true);
            } else if (componentType.equals("GROUP") && componentName.contains("dropdown")) {
                Dropdown dropdown = ((Group) component).convertToDropdown();
                form.frontendComponentList.add(dropdown);
                form.setContainDropdown(true);
            } else if ((componentType.equals("RECTANGLE") || componentType.equals("VECTOR")) && componentName.contains("background")) {
                switch (componentType) {
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
            } else if (componentType.equals("GROUP") && component.getName().toLowerCase().contains("slider")){
                Slider slider = ((Group) component).convertSlider();
                form.frontendComponentList.add(slider);
                form.setContainSlider(true);
            // Add recursion to form/card
            } else if (componentType.equals("GROUP")
                    && (component.getName().toLowerCase().contains("form")
                    || component.getName().toLowerCase().contains("card"))) {
                Form nestForm = ((Group)component).convertForm(projectID, auth);
                form.frontendComponentList.add(nestForm);
            } else {
                Image image = component.convertToImage();
                form.frontendComponentList.add(image);
                form.setContainImage(true);
            }
        }
        form.sortComponentByY();
        return form;
    }

    public void setWireframeBoundingBox(AbsoluteBoundingBox wireframeBoundingBox) {
        this.wireframeBoundingBox = wireframeBoundingBox;
    }

    /**
     *  @return Function used to convert a Group component into a actual slider
     *  with current value, minimum/maximum value within the original Figma File
     *  in the type of text.
     */
    public Slider convertSlider() {
        Slider slider = new Slider();
        slider.setHeight(this.getHeight());
        slider.setWidth(this.getWidth());
        slider.setPositionX(this.getPositionX());
        slider.setPositionY(this.getPositionY());
        slider.setAlign(this.getAlign());
        for (FigmaComponent component : this.componentMap.values()) {
            if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("cur_value")) {
                int cur_value = Integer.parseInt(((Text) component).getCharacters());
                slider.setCur_value(cur_value);
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("min_value")) {
                int min_value = Integer.parseInt(((Text) component).getCharacters());
                slider.setMin_value(min_value);
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("max_value")) {
                int max_value = Integer.parseInt(((Text) component).getCharacters());
                slider.setMax_value(max_value);
            }
        }
        return slider;
    }

    /**
     *  @return Function used to convert a Group component into a Fixed chart
     *  With nothing within the chart can be recognized under the current version.
     *  Only a fixed content chart will be generated at the position of the component.
     */
    public Chart convertToFixedChart() {
        Chart fixedChart = new Chart();
        fixedChart.setHeight(this.getHeight());
        fixedChart.setWidth(this.getWidth());
        fixedChart.setPositionX(this.getPositionX());
        fixedChart.setPositionY(this.getPositionY());
        fixedChart.setAlign(this.getAlign());

        return fixedChart;
    }

    /**
     *  @return Function used to convert a Group component into a dropdown
     *  with rectangle recognized as the container and
     *  the text named 'option' recognized as the first default option.
     */
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


}
