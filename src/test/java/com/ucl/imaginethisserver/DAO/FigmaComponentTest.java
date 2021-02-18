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

    static FigmaAPIUtil testFigmaApiUtil;
    static GenerationService testGenerationService;

    static final String TEST_PROJECT_ID = "testId";
    static final Authentication TEST_AUTH = null;

    @BeforeAll
    static void setUpMocks() throws FileNotFoundException, IOException {
        JsonReader reader = new JsonReader(new FileReader("src/test/java/resources/exampleFigmaProject.json"));
        JsonObject dataFile = new Gson().fromJson(reader, JsonObject.class);

        testFigmaApiUtil = mock(FigmaAPIUtil.class);
        testGenerationService = new GenerationServiceImpl(testFigmaApiUtil);

        // Mock only the API call, not the processing method
        when(testFigmaApiUtil.requestFigmaFile(TEST_PROJECT_ID, TEST_AUTH)).thenReturn(dataFile);
    }

    @Test
    void givenCorrectJSONFile_whenRequested_thenReturnCorrectFigmaFilePagesAndWireframes() {
        FigmaFile testFigmaFile = testGenerationService.getFigmaFile(TEST_PROJECT_ID, TEST_AUTH);
        // Firstly, check project high-level attributes
        assertEquals(TEST_PROJECT_ID, testFigmaFile.getProjectID());
        assertEquals("Testing application", testFigmaFile.getProjectName());
        assertEquals("2021-02-14T08:52:49Z", testFigmaFile.getLastModified());
        assertEquals("677374936", testFigmaFile.getVersion());

        List<Page> pages = testFigmaFile.getPages();
        List<Wireframe> wireframes = testFigmaFile.getWireframes();
        // Secondly, check there is 1 page inside the project with correct name and ID
        assertEquals(1, pages.size());
        assertEquals("0:1", pages.get(0).getId());
        assertEquals("Page 1", pages.get(0).getName());

        // Thirdly, check 2 wireframes inside the project with correct names
        assertEquals(2, wireframes.size());
        for (Wireframe wireframe : wireframes) {
            assertThat(wireframe.getId(), anyOf(is("1:2"), is("202:4")));
            assertThat(wireframe.getName(), anyOf(is("Wireframe1"), is("Wireframe2")));
        }
    }


    @Test
    void givenMalformedWireframeName_whenRequested_thenReturnSanitizedName() {
        Wireframe wireframe = new Wireframe();
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