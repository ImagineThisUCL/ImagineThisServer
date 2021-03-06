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
            Wireframe transitionWireframe = getFigmaFile().getWireframeById(getTransitionNodeID());
            imageButtonComponent.setTransitionNodeName(transitionWireframe.getName());
        }
        for (FigmaComponent component : getComponents()) {
            if (component.getName().matches(".*(image|icon|picture).*")) {
                imageButtonComponent.setImageURL(component.getImageURL());
            }
        }
        return imageButtonComponent;
    }

}
