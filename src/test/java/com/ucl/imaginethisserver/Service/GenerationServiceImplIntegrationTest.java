package com.ucl.imaginethisserver.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.ucl.imaginethisserver.Util.*;
import com.ucl.imaginethisserver.Service.ServiceImpl.GenerationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class GenerationServiceImplIntegrationTest {

    @Autowired
    private GenerationServiceImpl testGenerationService;

    @Autowired
    private CodeGenerator testCodeGenerator;

    @MockBean
    private FileUtil testFileUtil;

    @MockBean
    private FigmaAPIUtil testFigmaApiUtil;

    @Value("${config.outputStorageFolder}")
    private String OUTPUT_STORAGE_FOLDER;


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
        // Mock readFile method, because some operations can be performed with return strings
        when(testFileUtil.readFile(any())).thenReturn("");
    }

    // Complete full test of generation service, where based on a real Figma JSON file, all files are generated
    @Test
    void givenCorrectFigmaJSONFile_whenGeneratingCode_thenCorrectFilesAreCreated() throws IOException {

        testGenerationService.buildProject(TEST_PROJECT_ID, TEST_AUTH, TEST_WIREFRAME_LIST);

        String projectFolder = String.format("%s/%s", OUTPUT_STORAGE_FOLDER, TEST_PROJECT_ID);
        verify(testFileUtil).writeFile(eq(projectFolder + "/package.json"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/app.config.js"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/assets/BaseStyle.js"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/components/views/Wireframe1.js"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/components/views/Wireframe2.js"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/components/reusables/Button.js"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/components/reusables/P.js"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/App.js"), any());

        // Verify resulting folder is zipped
        verify(testFileUtil).zipDirectory(eq(projectFolder));
    }

}
