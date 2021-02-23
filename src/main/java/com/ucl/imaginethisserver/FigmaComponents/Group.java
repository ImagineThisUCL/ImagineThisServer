package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.JsonArray;
import com.ucl.imaginethisserver.Conf.ExcludeSerialization;

import java.util.ArrayList;
import java.util.List;

/**
 *  The object Group represents the Group Type on the Figma side.
 *  The object contains children component of the Group,
 *  the transitionNodeID that it links to (if there is any), and other values necessary.
*/
public abstract class Group extends FigmaComponent {
    @ExcludeSerialization
    private JsonArray children;
    private String transitionNodeID;

    private List<FigmaComponent> components = new ArrayList<>();

    public List<FigmaComponent> getComponents() {
        return components;
    }
    public JsonArray getChildren() {
        return children;
    }
    public String getTransitionNodeID() { return transitionNodeID; }

    public void setComponents(List<FigmaComponent> figmaComponents) {
        // Set objects to be empty
        components = new ArrayList<>();
        for (FigmaComponent component : figmaComponents) {
            components.add(component);
        }
    }

}
