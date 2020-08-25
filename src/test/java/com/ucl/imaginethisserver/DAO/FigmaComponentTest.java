package com.ucl.imaginethisserver.DAO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FigmaComponentTest {

    @Test
    void convertRelativePosition() {
        FigmaComponent figmaComponent = new Rectangle();
        figmaComponent.setAbsoluteBoundingBox(new AbsoluteBoundingBox(100, 50, 30, 20));

        AbsoluteBoundingBox wireframeBoundingBox = new AbsoluteBoundingBox(1000,1000, 10, 10);
        figmaComponent.convertRelativePosition(wireframeBoundingBox);
        assertEquals(100, figmaComponent.getWidth());
        assertEquals(50, figmaComponent.getHeight());
        assertEquals(20, figmaComponent.getPositionX());
        assertEquals(10, figmaComponent.getPositionY());
        assertEquals("LEFT",figmaComponent.getAlign());

        figmaComponent.setAbsoluteBoundingBox(new AbsoluteBoundingBox(100, 50, 730, 20));
        figmaComponent.convertRelativePosition(wireframeBoundingBox);
        assertEquals(720, figmaComponent.getPositionX());
        assertEquals("RIGHT",figmaComponent.getAlign());
    }

}