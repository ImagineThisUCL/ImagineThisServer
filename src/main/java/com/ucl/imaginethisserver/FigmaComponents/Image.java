package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.ImageComponent;

public class Image extends FigmaComponent {

    /**
     * Convert the current Figma component object to its corresponding Image Object
     * @return An Image Object
     */
    @Override
    public ImageComponent convertToFrontendComponent() {
        ImageComponent imageComponent = new ImageComponent();
        imageComponent.setWidth(getWidth());
        imageComponent.setHeight(getHeight());
        imageComponent.setPositionX(getPositionX());
        imageComponent.setPositionY(getPositionY());
        imageComponent.setAlign(getAlign());
        imageComponent.setImageURL(getImageURL());
        return imageComponent;
    }
}
