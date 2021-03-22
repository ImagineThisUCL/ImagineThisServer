package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.ReactComponents.MapComponent;

public class FigmaMap extends FigmaComponent {

    /**
     *  Function that converts a Figma Rectangle component into
     *  a frontend Map component,
     *  the map will inherit the basic size and position of the Rectangle
     */
    @Override
    public MapComponent convertToFrontendComponent() {
        MapComponent mapComponent = new MapComponent();
        mapComponent.setWidth(this.getWidth());
        mapComponent.setHeight(this.getHeight());
        mapComponent.setPositionX(this.getPositionX());
        mapComponent.setPositionY(this.getPositionY());
        mapComponent.setAlign(this.getAlign());

        return mapComponent;
    }

}
