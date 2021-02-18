package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.NavBarComponent;
import com.ucl.imaginethisserver.FrontendComponents.NavButtonComponent;
import com.ucl.imaginethisserver.Util.FrontendUtil;

public class Navigation extends Group {

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
        for (FigmaComponent component : components) {
            if (component.getType().equals("GROUP") && component.getName().contains("button")) {
                NavButtonComponent navButtonComponent = new NavButtonComponent();
                navButtonComponent.setWidth(component.getWidth());
                navButtonComponent.setHeight(component.getHeight());
                navButtonComponent.setPositionX(component.getPositionX());
                navButtonComponent.setPositionY(component.getPositionY());
                for (FigmaComponent childComponent : ((Group) component).getComponents()) {
                    if (childComponent.getType().equals("TEXT")) {
                        navButtonComponent.setText(((Text) childComponent).getCharacters());
                    } else if (childComponent.getName().toLowerCase().contains("image") || childComponent.getName().toLowerCase().contains("icon") || childComponent.getName().toLowerCase().contains("picture")) {
                        navButtonComponent.setIconURL(childComponent.getImageURL());
                    }
                }
                String transitionNodeID = ((Group) component).transitionNodeID;
                if (transitionNodeID != null && FrontendUtil.GENERATE_PAGE_LIST.contains(page.getWireframeByID(transitionNodeID).getName())) {
                    NavBarComponent.BUTTON_MAP.put(navButtonComponent.getText(), page.getWireframeByID(transitionNodeID).getName());
                } else {
                    NavBarComponent.BUTTON_MAP.put(navButtonComponent.getText(), "Placeholder");
                }
                NavBarComponent.NAV_BUTTONS.add(navButtonComponent);


            }
            if (component.getType().equals("RECTANGLE") && component.getName().contains("background")) {
                Rectangle rectangle = (Rectangle) component;
                NavBarComponent.containerFills = rectangle.getFills();
            }
        }
        return navBarComponent;
    }

}
