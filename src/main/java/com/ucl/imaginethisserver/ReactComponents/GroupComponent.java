package com.ucl.imaginethisserver.ReactComponents;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupComponent extends ReactComponent {

    private List<ReactComponent> components = new ArrayList<>();

    public List<ReactComponent> getComponents() { return components; }

    public void setComponents(List<ReactComponent> reactComponents) {
        components = new ArrayList<>();
        for (ReactComponent component : reactComponents) {
            components.add(component);
        }
    }

    public <T extends ReactComponent> boolean containsComponent(Class<T> cls) {
        return ReactComponent.containsComponent(components, cls);
    }

    /**
     *  Sort the components within the form to decide their vertical positioning
     */
    public void sortComponentsByY() {
        components.sort((o1, o2) -> {
            if (o1.getPositionY() > o2.getPositionY()) return 1;
            else return -1;
        });
    }

}
