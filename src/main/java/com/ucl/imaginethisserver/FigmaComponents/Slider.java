package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.SliderComponent;

public class Slider extends Group {

    /**
     *  @return Function used to convert a Group component into a actual slider
     *  with current value, minimum/maximum value within the original Figma File
     *  in the type of text.
     */
    public SliderComponent convertToFrontendComponent() {
        SliderComponent sliderComponent = new SliderComponent();
        sliderComponent.setHeight(this.getHeight());
        sliderComponent.setWidth(this.getWidth());
        sliderComponent.setPositionX(this.getPositionX());
        sliderComponent.setPositionY(this.getPositionY());
        sliderComponent.setAlign(this.getAlign());
        for (FigmaComponent component : components) {
            if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("cur_value")) {
                int cur_value = Integer.parseInt(((Text) component).getCharacters());
                sliderComponent.setCur_value(cur_value);
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("min_value")) {
                int min_value = Integer.parseInt(((Text) component).getCharacters());
                sliderComponent.setMin_value(min_value);
            } else if (component.getType().equals("TEXT") && component.getName().toLowerCase().contains("max_value")) {
                int max_value = Integer.parseInt(((Text) component).getCharacters());
                sliderComponent.setMax_value(max_value);
            }
        }
        return sliderComponent;
    }

}
