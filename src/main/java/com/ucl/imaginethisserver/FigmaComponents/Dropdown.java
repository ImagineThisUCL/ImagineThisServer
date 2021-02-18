package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.DropdownComponent;

public class Dropdown extends Group {

    /**
     *  @return Function used to convert a Group component into a dropdown
     *  with rectangle recognized as the container and
     *  the text named 'option' recognized as the first default option.
     */
    public DropdownComponent convertToFrontendComponent() {
        DropdownComponent dropdownComponent = new DropdownComponent();
        dropdownComponent.setHeight(this.getHeight());
        dropdownComponent.setWidth(this.getWidth());
        dropdownComponent.setPositionX(this.getPositionX());
        dropdownComponent.setPositionY(this.getPositionY());
        dropdownComponent.setAlign(this.getAlign());

        for (FigmaComponent component : components) {
            if (component.getType().equals("RECTANGLE")) {
                Rectangle rectangle = (Rectangle) component;
                dropdownComponent.setContainerFills(rectangle.getFills());
                dropdownComponent.setCornerRadius(rectangle.getCornerRadius());
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("option")) {
                Text text = (Text) component;
                dropdownComponent.setOption(text.getCharacters());
                dropdownComponent.setStyle(text.getStyle());
                dropdownComponent.setTextFills(text.getFills());
            }
        }

        return dropdownComponent;
    }

}
