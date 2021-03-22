package com.ucl.imaginethisserver.ReactComponents;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ReactComponentTest {

    @Test
    void isSameLine() {
        ReactComponent a = new ButtonComponent();
        ReactComponent b = new ButtonComponent();
        a.setWidth(100);
        a.setHeight(100);
        a.setPositionX(10);
        a.setPositionY(10);
        assertTrue(a.isSameLine(a));

        b.setWidth(100);
        b.setHeight(100);
        b.setPositionX(150);
        b.setPositionY(10);
        assertTrue(a.isSameLine(b));

        b.setPositionY(50);
        assertTrue(a.isSameLine(b));

        b.setPositionY(200);
        assertFalse(a.isSameLine(b));
    }

    @Test
    void getInlineComponentList() {
        ReactComponent a = new ButtonComponent();
        a.setWidth(30);
        a.setHeight(30);
        a.setPositionX(10);
        a.setPositionY(5);

        ReactComponent b = new ButtonComponent();
        b.setWidth(30);
        b.setHeight(30);
        b.setPositionX(10);
        b.setPositionY(40);

        ReactComponent c = new ButtonComponent();
        c.setWidth(30);
        c.setHeight(30);
        c.setPositionX(100);
        c.setPositionY(40);

        ReactComponent d = new ButtonComponent();
        d.setWidth(30);
        d.setHeight(30);
        d.setPositionX(10);
        d.setPositionY(100);

        assertEquals(false, a.isSameLine(b));
        assertEquals(false, a.isSameLine(c));
        assertEquals(false, a.isSameLine(d));
        assertEquals(true, b.isSameLine(c));
        assertEquals(false, b.isSameLine(d));
        assertEquals(false, c.isSameLine(d));

    }
}