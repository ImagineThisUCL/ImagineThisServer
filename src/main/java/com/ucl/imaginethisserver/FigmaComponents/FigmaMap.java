package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.MapComponent;

public class FigmaMap extends FigmaComponent {

    /**
     *  Function that converts a Figma Rectangle component into
     *  a frontend Map component,
     *  the map will inherit the basic size and position of the Rectangle
     */
    @Override
    public MapComponent convertToFrontendComponent() {
        MapComponent image = new MapComponent();
        image.setWidth(this.getWidth());
        image.setHeight(this.getHeight());
        image.setPositionX(this.getPositionX());
        image.setPositionY(this.getPositionY());
        image.setAlign(this.getAlign());

        return image;
    }

}
