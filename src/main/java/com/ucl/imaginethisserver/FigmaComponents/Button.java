package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.ButtonComponent;

public class Button extends Group {

    @Override
    public ButtonComponent convertToFrontendComponent() {

        ButtonComponent buttonComponent = new ButtonComponent();
        buttonComponent.setPositionX(this.getPositionX());
        buttonComponent.setPositionY(this.getPositionY());
        buttonComponent.setWidth(this.getWidth());
        buttonComponent.setHeight(this.getHeight());
        buttonComponent.setAlign(this.getAlign());

        if (getTransitionNodeID() != null) {
            buttonComponent.setTransitionNodeID(getTransitionNodeID());
            Wireframe transitionWireframe = getFigmaFile().getWireframeById(getTransitionNodeID());
            buttonComponent.setTransitionNodeName(transitionWireframe.getName());
        }

        for (FigmaComponent component : getComponents()) {
            if (component instanceof Rectangle) {
                buttonComponent.setCornerRadius(component.getCornerRadius());
                buttonComponent.setRecFills(component.getFills());
                buttonComponent.setBorderColor(component.getStrokes(0).getColor());
                buttonComponent.setBorderWidth(component.getStrokeWeight());
            } else if (component instanceof Text) {
                Text text = (Text) component;
                buttonComponent.setCharacter(text.getCharacters());
                buttonComponent.setStyle(text.getStyle());
                buttonComponent.setTextFills(((Text) component).getFills());
            } else if (component instanceof Vector) {
                Vector vector = (Vector) component;
                buttonComponent.setCornerRadius(vector.getCornerRadius());
                buttonComponent.setRecFills(vector.getFills());
                if (vector.getStrokes().size() > 0) {
                    buttonComponent.setBorderColor(vector.getStrokes(0).getColor());
                }
                buttonComponent.setBorderWidth(vector.getStrokeWeight());
            } else if (component instanceof Ellipse) {
                    Ellipse ellipse = (Ellipse) component;
                    buttonComponent.setCircle(true);
                    buttonComponent.setRecFills(ellipse.getFills());
                    if (ellipse.getStrokes().size() > 0) {
                        buttonComponent.setBorderColor(ellipse.getStrokes().get(0).getColor());
                    }
                    buttonComponent.setBorderWidth(ellipse.getStrokeWeight());
            }
        }
        return buttonComponent;
    }

}
