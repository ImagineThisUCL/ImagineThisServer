package com.ucl.imaginethisserver.FrontendComponent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrontendComponentTest {

    @Test
    void isSameLine() {
        FrontendComponent a = new Button();
        FrontendComponent b = new Button();
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
}