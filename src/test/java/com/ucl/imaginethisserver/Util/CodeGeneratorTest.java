package com.ucl.imaginethisserver.Util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import com.ucl.imaginethisserver.Service.ServiceImpl.GenerationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CodeGeneratorTest {

    @MockBean
    private FileUtil testFileUtil;

    @Autowired
    private CodeGenerator testCodeGenerator;


    static final String TEST_PROJECT_ID = "testId";
    static final String TEST_OUTPUT_FOLDER = "testFolderPath";
    static final FigmaFile testFigmaFile = new FigmaFile(TEST_PROJECT_ID);
    static JsonObject testDataFile;


    // Prepare test suite with expensive operations just once before all tests
    @BeforeAll
    static void setupResources() throws FileNotFoundException, IOException {
        JsonReader reader = new JsonReader(new FileReader("src/test/java/resources/exampleFigmaProject.json"));
        testDataFile = new Gson().fromJson(reader, JsonObject.class);
    }

    @BeforeEach
    void setUpMocks() throws IOException {
        // Setup random output dummy folder
        testCodeGenerator.setOutputStorageFolder(TEST_OUTPUT_FOLDER);
    }

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectFoldersAreCreated() throws IOException {
        testCodeGenerator.generateOutputFolder(testFigmaFile);

        // Verify output folder is firstly delete, and then correct folders are created
        String projectFolder = TEST_OUTPUT_FOLDER + "/" + TEST_PROJECT_ID;
        verify(testFileUtil).deleteDirectory(eq(TEST_OUTPUT_FOLDER));
        verify(testFileUtil).makeDirectory(eq(TEST_OUTPUT_FOLDER));
        verify(testFileUtil).makeDirectory(eq(projectFolder));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/components"));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/components/views"));
    }

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectPackageFilesAreCreated() throws IOException {
        String correctPackageJsonFile = testCodeGenerator.getOutputStorageFolder() + "/" + TEST_PROJECT_ID + "/package.json";
        String correctAppConfigFile = testCodeGenerator.getOutputStorageFolder() + "/" + TEST_PROJECT_ID + "/app.config.js";

        testCodeGenerator.generatePackageFiles(testFigmaFile);

        // Verify that the method writes package.json and app.config.js files
        verify(testFileUtil).writeFile(eq(correctPackageJsonFile), any());
        verify(testFileUtil).writeFile(eq(correctAppConfigFile), any());
    }



}
