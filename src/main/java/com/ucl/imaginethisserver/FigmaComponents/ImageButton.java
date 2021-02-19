package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.ImageButtonComponent;

public class ImageButton extends Group {

    /**
     *  @return Function used to convert a Group component into a Image Button component,
     *  by dealing with its transitionNodeID, image within that function as the button view and
     *  the text contains within and etc.
     */
    @Override
    public ImageButtonComponent convertToFrontendComponent() {
        ImageButtonComponent imageButtonComponent = new ImageButtonComponent();
        imageButtonComponent.setPositionX(this.getPositionX());
        imageButtonComponent.setPositionY(this.getPositionY());
        imageButtonComponent.setWidth(this.getWidth());
        imageButtonComponent.setHeight(this.getHeight());
        imageButtonComponent.setAlign(this.getAlign());
        if (getTransitionNodeID() != null) {
            imageButtonComponent.setTransitionNodeID(getTransitionNodeID());
        }
        for (FigmaComponent component : components) {
            if ((component instanceof Group || component instanceof Rectangle) && (component.getName().contains("image|icon|picture"))) {
                imageButtonComponent.setImageURL(component.getImageURL());
            }
        }
        return imageButtonComponent;
    }

}
