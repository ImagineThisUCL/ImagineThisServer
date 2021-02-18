package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.ucl.imaginethisserver.Service.GenerationService;
import com.ucl.imaginethisserver.Service.ServiceImpl.GenerationServiceImpl;
import com.ucl.imaginethisserver.Util.Authentication;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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