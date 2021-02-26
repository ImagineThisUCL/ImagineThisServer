package com.ucl.imaginethisserver.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.ucl.imaginethisserver.Util.*;
import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import com.ucl.imaginethisserver.FigmaComponents.Page;
import com.ucl.imaginethisserver.FigmaComponents.Wireframe;
import com.ucl.imaginethisserver.Service.ServiceImpl.GenerationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GenerationServiceImplTest {

    @MockBean
    private FigmaAPIUtil testFigmaApiUtil;

    @MockBean
    private CodeGenerator testCodeGenerator;

    @MockBean
    private ExpoUtil testExpoUtil;

    @MockBean
    private FileUtil testFileUtil;

    @Autowired
    private GenerationServiceImpl testGenerationService;

    static final String TEST_PROJECT_ID = "testId";
    static final Authentication TEST_AUTH = null;
    static final List<String> TEST_WIREFRAME_LIST = Arrays.asList("Wireframe 1", "Wireframe 2");
    static JsonObject testDataFile;


    // Prepare test suite with expensive operations just once before all tests
    @BeforeAll
    static void setupResources() throws FileNotFoundException, IOException {
        JsonReader reader = new JsonReader(new FileReader("src/test/java/resources/exampleFigmaProject.json"));
        testDataFile = new Gson().fromJson(reader, JsonObject.class);
    }

    @BeforeEach
    void setupMocks() throws IOException {
        // Mock only the API call, not the processing method
        when(testFigmaApiUtil.requestFigmaFile(TEST_PROJECT_ID, TEST_AUTH)).thenReturn(testDataFile);
    }

    // Make sure that GenerationServiceImpl correctly parses real Figma JSON files
    @Test
    void givenCorrectJSONFile_whenRequested_thenItIsCorrectlyParsed() {
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

    // Make sure that during code generation, generator is called to create appropriate resources
    @Test
    void givenFigmaFile_whenBuildProjectCalled_thenGenerateAppropriateResources() throws IOException {
        testGenerationService.buildProject(TEST_PROJECT_ID, TEST_AUTH, TEST_WIREFRAME_LIST);
        // Verify correct components are built
        verify(testCodeGenerator).generateOutputFolder(any());
        verify(testCodeGenerator).generatePackageFiles(any());
        verify(testCodeGenerator).generateWireframes(any());
        verify(testCodeGenerator).generateReusableComponents(any());
        verify(testCodeGenerator).generateAppJSCode(any());

        // Make sure whole directory is zipped
        verify(testFileUtil).zipDirectory(any());

        // Make sure that Docker publishing container is started
        verify(testExpoUtil).publish(any());
    }

}
