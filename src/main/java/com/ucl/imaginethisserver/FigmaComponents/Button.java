package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.ReactComponents.ButtonComponent;

public class Button extends Group {

    @Override
    public ButtonComponent convertToFrontendComponent() {

        ButtonComponent buttonComponent = new ButtonComponent();
        buttonComponent.setPositionX(this.getPositionX());
        buttonComponent.setPositionY(this.getPositionY());
        buttonComponent.setWidth(this.getWidth());
        buttonComponent.setHeight(this.getHeight());
        buttonComponent.setAlign(this.getAlign());

        // Set button's transition to another wireframe if it exists
        if (getTransitionNodeID() != null) {
            FigmaWireframe transitionWireframe = getFigmaFile().getWireframeById(getTransitionNodeID());
            if (transitionWireframe != null) {
                buttonComponent.setTransitionNodeID(getTransitionNodeID());
                buttonComponent.setTransitionNodeName(transitionWireframe.getName());
            }
        }

        for (FigmaComponent component : getComponents()) {
            if (component instanceof Text) {
                Text text = (Text) component;
                buttonComponent.setCharacters(text.getCharacters());
                buttonComponent.setStyle(text.getStyle());
                buttonComponent.setTextFills(component.getFills());
            } else if (component instanceof Rectangle || component instanceof Vector) {
                buttonComponent.setCornerRadius(component.getCornerRadius());
                buttonComponent.setContainerFills(component.getFills());
                buttonComponent.setBorderWidth(component.getStrokeWeight());
                if (!component.getStrokes().isEmpty()) {
                    buttonComponent.setBorderColor(component.getStrokes(0).getColor());
                }
            } else if (component instanceof Ellipse) {
                buttonComponent.setCircle(true);
                buttonComponent.setContainerFills(component.getFills());
                buttonComponent.setBorderWidth(component.getStrokeWeight());
                if (!component.getStrokes().isEmpty()) {
                    buttonComponent.setBorderColor(component.getStrokes().get(0).getColor());
                }
            }
        }
        return buttonComponent;
    }

}
