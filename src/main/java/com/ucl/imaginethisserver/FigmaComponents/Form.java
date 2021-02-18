package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.*;

public class Form extends Group {

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
    public FormComponent convertToFrontendComponent() {
        FormComponent formComponent = new FormComponent();
        formComponent.setHeight(this.getHeight());
        formComponent.setWidth(this.getWidth());
        formComponent.setPositionX(this.getPositionX());
        formComponent.setPositionY(this.getPositionY());
        formComponent.setAlign(this.getAlign());
        for (FigmaComponent component : components) {
            String componentType = component.getType();
            String componentName = component.getName().toLowerCase();
            if (componentType.equals("TEXT")) {
                TextComponent text = ((Text) component).convertToFrontendText();
                formComponent.frontendComponentList.add(text);
                formComponent.setContainText(true);
            } else if (componentName.contains("switch")) {
                SwitchComponent aSwitch = component.convertSwitch();
                formComponent.frontendComponentList.add(aSwitch);
                formComponent.setContainSwitch(true);
            } else if (componentType.equals("GROUP") && componentName.contains("input")) {
                TextBoxComponent textBoxComponent = ((Group) component).convertTextBox();
                formComponent.frontendComponentList.add(textBoxComponent);
                formComponent.setContainTextBox(true);
            } else if (componentType.equals("GROUP") && componentName.contains("textbutton")) {
                ButtonComponent buttonComponent = ((Group) component).convertButton();
                formComponent.frontendComponentList.add(buttonComponent);
                formComponent.setContainButton(true);
            } else if (componentType.equals("GROUP") && componentName.contains("imagebutton")) {
                ImageButtonComponent imageButtonComponent = ((Group) component).convertImageButton(projectID, auth);
                formComponent.frontendComponentList.add(imageButtonComponent);
                formComponent.setContainImageButton(true);
            } else if ((componentType.equals("RECTANGLE") || componentType.equals("GROUP")) && (componentName.contains("image") || componentName.contains("icon") || componentName.contains("picture"))) {
                ImageComponent imageComponent = component.convertToImage();
                formComponent.frontendComponentList.add(imageComponent);
                formComponent.setContainImage(true);
            } else if (componentType.equals("GROUP") && componentName.contains("chart")) {
                ChartComponent fixedChartComponent = ((Group) component).convertToFixedChart();
                formComponent.frontendComponentList.add(fixedChartComponent);
                formComponent.setContainChart(true);
            } else if (componentType.equals("GROUP") && componentName.contains("dropdown")) {
                DropdownComponent dropdownComponent = ((Group) component).convertToDropdown();
                formComponent.frontendComponentList.add(dropdownComponent);
                formComponent.setContainDropdown(true);
            } else if ((componentType.equals("RECTANGLE") || componentType.equals("VECTOR")) && componentName.contains("background")) {
                switch (componentType) {
                    case "RECTANGLE":
                        Rectangle rectangle = (Rectangle) component;
                        if (rectangle.getFills().size() > 0) {
                            formComponent.setBackgroundColor(rectangle.getFills().get(0).getColor());
                        }
                        formComponent.setCornerRadius(rectangle.getCornerRadius());
                        if (rectangle.getStrokes().size() > 0) {
                            formComponent.setBorderColor(rectangle.getStrokes().get(0).getColor());
                        }
                        formComponent.setBorderWidth(rectangle.getStrokeWeight());

                        break;
                    case "VECTOR":
                        Vector vector = (Vector) component;
                        if (vector.getFills().size() > 0) {
                            formComponent.setBackgroundColor(vector.getFills().get(0).getColor());
                        }
                        formComponent.setCornerRadius((vector.getCornerRadius()));
                        if (vector.getStrokes().size() > 0) {
                            formComponent.setBorderColor(vector.getStrokes().get(0).getColor());
                        }
                        formComponent.setBorderWidth(vector.getStrokeWeight());
                        break;
                }
            } else if (componentType.equals("GROUP") && component.getName().toLowerCase().contains("slider")){
                SliderComponent sliderComponent = ((Group) component).convertSlider();
                formComponent.frontendComponentList.add(sliderComponent);
                formComponent.setContainSlider(true);
                // Add recursion to form/card
            } else if (componentType.equals("GROUP")
                    && (component.getName().toLowerCase().contains("form")
                    || component.getName().toLowerCase().contains("card"))) {
                FormComponent nestFormComponent = ((Group)component).convertForm(projectID, auth);
                formComponent.frontendComponentList.add(nestFormComponent);
            } else {
                ImageComponent imageComponent = component.convertToImage();
                formComponent.frontendComponentList.add(imageComponent);
                formComponent.setContainImage(true);
            }
        }
        formComponent.sortComponentByY();
        return formComponent;
    }
}
