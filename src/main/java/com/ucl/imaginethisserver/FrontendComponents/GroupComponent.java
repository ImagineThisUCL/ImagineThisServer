package com.ucl.imaginethisserver.FrontendComponents;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupComponent extends FrontendComponent {

    private List<FrontendComponent> components = new ArrayList<>();

    public List<FrontendComponent> getComponents() { return components; }

    public void setComponents(List<FrontendComponent> frontendComponents) {
        components = new ArrayList<>();
        for (FrontendComponent component : frontendComponents) {
            components.add(component);
        }
    }

    public <T extends FrontendComponent> boolean containsComponent(Class<T> cls) {
        return FrontendComponent.containsComponent(components, cls);
    };

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
