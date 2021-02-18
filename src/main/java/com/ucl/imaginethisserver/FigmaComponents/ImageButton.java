package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.ImageButtonComponent;

public class ImageButton extends Group {

    /**
     *  @return Function used to convert a Group component into a Image Button component,
     *  by dealing with its transitionNodeID, image within that function as the button view and
     *  the text contains within and etc.
     */
    public ImageButtonComponent convertToFrontendComponent() {
        ImageButtonComponent imageButtonComponent = new ImageButtonComponent();
        imageButtonComponent.setPositionX(this.getPositionX());
        imageButtonComponent.setPositionY(this.getPositionY());
        imageButtonComponent.setWidth(this.getWidth());
        imageButtonComponent.setHeight(this.getHeight());
        imageButtonComponent.setAlign(this.getAlign());
        if (this.transitionNodeID != null) {
            imageButtonComponent.setTransitionNodeID(this.transitionNodeID);
        }
        for (FigmaComponent component : components) {
            if ((component.getType().equals("GROUP") || component.getType().equals("RECTANGLE")) && (component.getName().toLowerCase().contains("image")||component.getName().toLowerCase().contains("icon")||component.getName().toLowerCase().contains("picture"))) {
                imageButtonComponent.setImageURL(component.getImageURL());
            }
        }
        return imageButtonComponent;
    }

}
