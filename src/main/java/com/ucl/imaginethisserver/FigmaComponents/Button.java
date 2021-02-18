package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.ButtonComponent;

public class Button extends Group {

    public ButtonComponent convertToFrontendComponent() {

        ButtonComponent buttonComponent = new ButtonComponent();
        buttonComponent.setPositionX(this.getPositionX());
        buttonComponent.setPositionY(this.getPositionY());
        buttonComponent.setWidth(this.getWidth());
        buttonComponent.setHeight(this.getHeight());
        buttonComponent.setAlign(this.getAlign());
        if (this.transitionNodeID != null) {
            buttonComponent.setTransitionNodeID(this.transitionNodeID);
        }
        for (FigmaComponent component : components) {
            switch (component.getType()) {
                case "RECTANGLE" :
                    Rectangle rectangle = (Rectangle) component;
                    buttonComponent.setCornerRadius(rectangle.getCornerRadius());
                    buttonComponent.setRecFills(rectangle.getFills());
                    if (rectangle.getStrokes().size() > 0) {
                        buttonComponent.setBorderColor(rectangle.getStrokes().get(0).getColor());
                    }
                    buttonComponent.setBorderWidth(rectangle.getStrokeWeight());
                    break;
                case "TEXT":
                    Text text = (Text) component;
                    buttonComponent.setCharacter(text.getCharacters());
                    buttonComponent.setStyle(text.getStyle());
                    buttonComponent.setTextFills(((Text) component).getFills());
                    break;
                case "VECTOR":
                    Vector vector = (Vector) component;
                    buttonComponent.setCornerRadius(vector.getCornerRadius());
                    buttonComponent.setRecFills(vector.getFills());
                    if (vector.getStrokes().size() > 0) {
                        buttonComponent.setBorderColor(vector.getStrokes().get(0).getColor());
                    }
                    buttonComponent.setBorderWidth(vector.getStrokeWeight());
                    break;
                case "ELLIPSE":
                    Ellipse ellipse = (Ellipse) component;
                    buttonComponent.setCircle(true);
                    buttonComponent.setRecFills(ellipse.getFills());
                    if (ellipse.getStrokes().size() > 0) {
                        buttonComponent.setBorderColor(ellipse.getStrokes().get(0).getColor());
                    }
                    buttonComponent.setBorderWidth(ellipse.getStrokeWeight());
                    break;
            }
        }
        return buttonComponent;
    }

}
