package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.TextBoxComponent;

public class TextBox extends Group {

    public TextBoxComponent convertToFrontendComponent() {
        TextBoxComponent textbox = new TextBoxComponent();
        textbox.setPositionX(this.getPositionX());
        textbox.setPositionY(this.getPositionY());
        textbox.setWidth(this.getWidth());
        textbox.setHeight(this.getHeight());
        textbox.setAlign(this.getAlign());

        for (FigmaComponent component : components) {
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

}
