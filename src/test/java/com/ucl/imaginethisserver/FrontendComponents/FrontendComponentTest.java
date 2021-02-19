package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.FigmaComponents.Text;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FrontendComponentTest {

    @Test
    void testTextComponentReusableCode() throws IOException {
        TextComponent spyComponent = spy(new TextComponent());
        doNothing().when(spyComponent).readTemplateFile(any());
        assertEquals(true, spyComponent.isReusable());

        String code = spyComponent.generateReusableCode();
        verify(spyComponent).readTemplateFile("P.js");
    }

    @Test
    void isSameLine() {
        FrontendComponent a = new ButtonComponent();
        FrontendComponent b = new ButtonComponent();
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
        FrontendComponent a = new ButtonComponent();
        a.setWidth(30);
        a.setHeight(30);
        a.setPositionX(10);
        a.setPositionY(5);

        FrontendComponent b = new ButtonComponent();
        b.setWidth(30);
        b.setHeight(30);
        b.setPositionX(10);
        b.setPositionY(40);

        FrontendComponent c = new ButtonComponent();
        c.setWidth(30);
        c.setHeight(30);
        c.setPositionX(100);
        c.setPositionY(40);

        FrontendComponent d = new ButtonComponent();
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