package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.TextBoxComponent;

public class TextBox extends Group {

    @Override
    public TextBoxComponent convertToFrontendComponent() {
        TextBoxComponent textbox = new TextBoxComponent();
        textbox.setPositionX(getPositionX());
        textbox.setPositionY(getPositionY());
        textbox.setWidth(getWidth());
        textbox.setHeight(getHeight());
        textbox.setAlign(getAlign());

        for (FigmaComponent component : getComponents()) {
            if (component instanceof Rectangle || component instanceof Vector) {
                textbox.setContainerFills(component.getFills());
                textbox.setCornerRadius(component.getCornerRadius());

            } else if (component instanceof Text && component.getName().contains("placeholder")) {
                Text text = (Text) component;
                textbox.setPlaceholder(text.getCharacters());
                textbox.setStyle(text.getStyle());
                textbox.setTextFills(text.getFills());
            } else if (component instanceof Text && component.getName().toLowerCase().contains("label")) {
                Text text = (Text) component;
                textbox.setLabel(text.getCharacters());
                textbox.setLabelFills(text.getFills());
            }
        }

        return textbox;
    }

}
