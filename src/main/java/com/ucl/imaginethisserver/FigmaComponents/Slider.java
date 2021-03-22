package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.ReactComponents.SliderComponent;

public class Slider extends Group {

    /**
     *  @return Function used to convert a Group component into a actual slider
     *  with current value, minimum/maximum value within the original Figma File
     *  in the type of text.
     */
    @Override
    public SliderComponent convertToFrontendComponent() {
        SliderComponent sliderComponent = new SliderComponent();
        sliderComponent.setHeight(this.getHeight());
        sliderComponent.setWidth(this.getWidth());
        sliderComponent.setPositionX(this.getPositionX());
        sliderComponent.setPositionY(this.getPositionY());
        sliderComponent.setAlign(this.getAlign());
        for (FigmaComponent component : getComponents()) {
            if (component instanceof Text && component.getName().contains("cur_value")) {
                int currentValue = Integer.parseInt(((Text) component).getCharacters());
                sliderComponent.setCurrentValue(currentValue);
            } else if (component instanceof Text && component.getName().contains("min_value")) {
                int minValue = Integer.parseInt(((Text) component).getCharacters());
                sliderComponent.setMinValue(minValue);
            } else if (component instanceof Text && component.getName().contains("max_value")) {
                int maxValue = Integer.parseInt(((Text) component).getCharacters());
                sliderComponent.setMaxValue(maxValue);
            }
        }
        return sliderComponent;
    }

}
