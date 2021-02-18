package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.ImageComponent;

public class Image extends Group {

    /**
     * Convert the current Figma component object to its corresponding Image Object
     * @return An Image Object
     */
    public ImageComponent convertToFrontendComponent() {
        ImageComponent imageComponent = new ImageComponent();
        imageComponent.setWidth(this.getWidth());
        imageComponent.setHeight(this.getHeight());
        imageComponent.setPositionX(this.getPositionX());
        imageComponent.setPositionY(this.getPositionY());
        imageComponent.setAlign(this.getAlign());
        imageComponent.setImageURL(this.getImageURL());
        return imageComponent;
    }
}
