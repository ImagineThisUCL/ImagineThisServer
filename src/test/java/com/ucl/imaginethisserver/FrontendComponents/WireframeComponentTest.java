package com.ucl.imaginethisserver.FrontendComponents;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WireframeComponentTest {

    @Test
    void getInlineComponentList() {
        ArrayList<FrontendComponent> frontendComponents = new ArrayList<>();
        FrontendComponent a = new ButtonComponent();
        a.setWidth(30);
        a.setHeight(30);
        a.setPositionX(10);
        a.setPositionY(5);
        frontendComponents.add(a);

        FrontendComponent b = new ButtonComponent();
        b.setWidth(30);
        b.setHeight(30);
        b.setPositionX(10);
        b.setPositionY(40);
        frontendComponents.add(b);

        FrontendComponent c = new ButtonComponent();
        c.setWidth(30);
        c.setHeight(30);
        c.setPositionX(100);
        c.setPositionY(40);
        frontendComponents.add(c);

        FrontendComponent d = new ButtonComponent();
        d.setWidth(30);
        d.setHeight(30);
        d.setPositionX(10);
        d.setPositionY(100);
        frontendComponents.add(d);

        WireframeComponent wireframeComponent = new WireframeComponent(frontendComponents);
        List<List<FrontendComponent>> inlineList = wireframeComponent.getInlineComponentList();
        assertEquals(3, inlineList.size());
        assertEquals(1, inlineList.get(0).size());
        assertEquals(2, inlineList.get(1).size());
        assertEquals(1, inlineList.get(2).size());
    }

}
