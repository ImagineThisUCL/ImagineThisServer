package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 *  The object Group represents the Group Type on the Figma side.
 *  The object contains children component of the Group,
 *  the transitionNodeID that it links to (if there is any), and other values necessary.
*/
abstract public class Group extends FigmaComponent {
    JsonArray children;
    @Expose()
    String blendMode;
    String transitionNodeID;

    protected List<FigmaComponent> components;
    private AbsoluteBoundingBox wireframeBoundingBox;

    public List<FigmaComponent> getComponents() {
        return components;
    }

    public void setComponents(List<FigmaComponent> figmaComponents) {
        // Set objects to be empty
        components = new ArrayList<>();
        for (FigmaComponent component : figmaComponents) {
            components.add(component);
        }
    }

    public JsonArray getChildren() {
        return children;
    }




    public void setWireframeBoundingBox(AbsoluteBoundingBox wireframeBoundingBox) {
        this.wireframeBoundingBox = wireframeBoundingBox;
    }



}
