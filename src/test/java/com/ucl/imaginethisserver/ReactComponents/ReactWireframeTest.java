package com.ucl.imaginethisserver.ReactComponents;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReactWireframeTest {

    @Test
    void getInlineComponentList() {
        ArrayList<ReactComponent> reactComponents = new ArrayList<>();
        ReactComponent a = new ButtonComponent();
        a.setWidth(30);
        a.setHeight(30);
        a.setPositionX(10);
        a.setPositionY(5);
        reactComponents.add(a);

        ReactComponent b = new ButtonComponent();
        b.setWidth(30);
        b.setHeight(30);
        b.setPositionX(10);
        b.setPositionY(40);
        reactComponents.add(b);

        ReactComponent c = new ButtonComponent();
        c.setWidth(30);
        c.setHeight(30);
        c.setPositionX(100);
        c.setPositionY(40);
        reactComponents.add(c);

        ReactComponent d = new ButtonComponent();
        d.setWidth(30);
        d.setHeight(30);
        d.setPositionX(10);
        d.setPositionY(100);
        reactComponents.add(d);

        ReactWireframe reactWireframe = new ReactWireframe(reactComponents);
        List<List<ReactComponent>> inlineList = ReactComponent.getInlineComponentList(reactWireframe.getComponents());
        assertEquals(3, inlineList.size());
        assertEquals(1, inlineList.get(0).size());
        assertEquals(2, inlineList.get(1).size());
        assertEquals(1, inlineList.get(2).size());
    }

}
