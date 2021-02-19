package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.*;

import java.util.ArrayList;
import java.util.List;

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

        // Process all children
        List<FrontendComponent> frontendComponents = new ArrayList<>();
        for (FigmaComponent component : components) {
            frontendComponents.add(component.convertToFrontendComponent());

            // Process background of applicable
            if (component.getName().contains("background")) {
                formComponent.setBackgroundColor(component.getFills(0).getColor());
                formComponent.setCornerRadius(component.getCornerRadius());
                formComponent.setBorderColor(component.getStrokes(0).getColor());
                formComponent.setBorderWidth(component.getStrokeWeight());
            }
        }
        formComponent.setComponents(frontendComponents);

        return formComponent;
    }
}
