package com.ucl.imaginethisserver.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FigmaComponentTest {

    static FigmaAPIUtil figmaApiUtil;
    static final String TEST_PROJECT_ID = "testId";

    @BeforeAll
    static void setUpMocks() throws FileNotFoundException, IOException {
        // Spies are partial mocks, good for mocking objects, see https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#spy
        // In this case it is needed because of the constructor
        figmaApiUtil = spy(new FigmaAPIUtil(TEST_PROJECT_ID, null));
        JsonReader reader = new JsonReader(new FileReader("src/test/java/resources/exampleFigmaProject.json"));
        JsonObject dataFile = new Gson().fromJson(reader, JsonObject.class);
        // Mock only the API call, not the processing method
        doReturn(dataFile).when(figmaApiUtil).requestFigmaFile();
        doReturn("randomURL").when(figmaApiUtil).getComponentImageURL(any());
        doNothing().when(figmaApiUtil).fetchComponentImageURLs(any()); // Mock method for pre-fetching values into cache
    }

    @Test
    void givenCorrectJSONFile_whenRequested_thenReturnCorrectFigmaFile() {
        FigmaFile testFigmaFile = figmaApiUtil.getFigmaFile();
        // Firstly, check project high-level attributes
        assertEquals(TEST_PROJECT_ID, testFigmaFile.getProjectID());
        assertEquals("Testing application", testFigmaFile.getProjectName());
        assertEquals("2021-02-14T08:52:49Z", testFigmaFile.getLastModified());
        assertEquals("677374936", testFigmaFile.getVersion());

        List<Page> pages = testFigmaFile.getPages();
        List<Wireframe> wireframes = testFigmaFile.getWireframes();
        // Secondly, check there is 1 page inside the project
        assertEquals(1, pages.size());

        // Thirdly, check 2 wireframes inside the project
        assertEquals(2, wireframes.size());
    }

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