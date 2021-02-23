package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.NavBarComponent;
import com.ucl.imaginethisserver.FrontendComponents.NavigationButtonComponent;

public class Navigation extends Group {

    @Override
    public NavBarComponent convertToFrontendComponent() {

// TODO: didn't understand this code
//        for (String navText : NavBar.BUTTON_MAP.keySet()) {
//            if (NavBar.BUTTON_MAP.get(navText).equals("Placeholder")) {
//                NavBar.BUTTON_MAP.put(navText, wireframe.getName());
//            }
//        }

        NavBarComponent navBarComponent = new NavBarComponent();
        navBarComponent.setHeight(this.getHeight());
        navBarComponent.setWidth(this.getWidth());
        navBarComponent.setPositionX(this.getPositionX());
        navBarComponent.setPositionY(this.getPositionY());

        for (FigmaComponent component : getComponents()) {
            if (component instanceof Group && component.getName().contains("button")) {
                NavigationButtonComponent button = new NavigationButtonComponent();
                button.setWidth(component.getWidth());
                button.setHeight(component.getHeight());
                button.setPositionX(component.getPositionX());
                button.setPositionY(component.getPositionY());
                for (FigmaComponent childComponent : ((Group) component).getComponents()) {
                    if (childComponent instanceof Text) {
                        button.setText(((Text) childComponent).getCharacters());
                    } else if (childComponent.getName().matches("image|icon|picture")) {
                        button.setImageURL(childComponent.getImageURL());
                    }
                }
                button.setTransitionNodeID(((Group) component).getTransitionNodeID());

            } else if (component instanceof Rectangle && component.getName().contains("background")) {
                Rectangle rectangle = (Rectangle) component;
                navBarComponent.setBackgroundColor(rectangle.getFills(0).getColor());
            }
        }
        return navBarComponent;
    }

}
