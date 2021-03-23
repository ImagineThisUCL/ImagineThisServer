package com.ucl.imaginethisserver.FigmaComponents;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FigmaWireframeTest {

    @Test
    void givenMalformedWireframeName_whenRequested_thenReturnSanitizedName() {
        FigmaWireframe wireframe = new FigmaWireframe();
        // Make sure wireframe names are capitalized, because they will form React classes
        wireframe.setName("wireframe");
        assertEquals("Wireframe", wireframe.getName());
        // Make sure any white spaces are removed
        wireframe.setName("wireframe name");
        assertEquals("Wireframename", wireframe.getName());
        // Make sure any special characters are removed
        wireframe.setName("wireframe*5^8");
        assertEquals("Wireframe58", wireframe.getName());
    }
}
