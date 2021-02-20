package com.ucl.imaginethisserver.Util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.ucl.imaginethisserver.FigmaComponents.*;
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
import java.util.ArrayList;
import java.util.List;

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
    static FigmaFile testFigmaFile;

    @BeforeEach
    void setUpMocks() throws IOException {
        // Initialize new empty FigmaFile
        testFigmaFile = new FigmaFile(TEST_PROJECT_ID);
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
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/assets"));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/components"));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/components/views"));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/components/reusables"));
    }

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectPackageFilesAreCreated() throws IOException {
        testCodeGenerator.generatePackageFiles(testFigmaFile);

        String projectFolder = testCodeGenerator.getOutputStorageFolder() + "/" + TEST_PROJECT_ID;
        // Verify that correct default files are generated
        verify(testFileUtil).writeFile(eq(projectFolder + "/package.json"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/app.config.js"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/assets/BaseStyle.js"), any());
    }

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectReusableFilesAreCreated() throws IOException {
        Page testPage = new Page();
        Wireframe testWireframe = new Wireframe();
        List<FigmaComponent> testComponents = new ArrayList<>();
        // Add some random components, some of which need a reusable component
        testComponents.add(new Button());
        testComponents.add(new Text());
        testComponents.add(new Chart());
        testComponents.add(new Rectangle()); // Does not use reusable component
        testComponents.add(new Slider());
        testComponents.add(new Dropdown());
        testComponents.add(new Image()); // Does not use reusable component
        testComponents.add(new FigmaMap());
        testComponents.add(new ImageButton());
        testComponents.add(new TextBox());
        testComponents.add(new Switch());
        testWireframe.setComponents(testComponents);
        testPage.addWireframe(testWireframe);
        testFigmaFile.addPage(testPage);

        testCodeGenerator.generateReusableComponents(testFigmaFile);

        String reusablesFolder = testCodeGenerator.getOutputStorageFolder() + "/" + TEST_PROJECT_ID + "/components/reusables";
        // Verify that correct reusable files are generated
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/Button.js"), any());
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/P.js"), any());
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/Chart.js"), any());
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/CustomSlider.js"), any());
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/Dropdown.js"), any());
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/GoogleMap.js"), any());
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/ImageButton.js"), any());
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/InputField.js"), any());
        verify(testFileUtil).writeFile(eq(reusablesFolder + "/Toggle.js"), any());
    }


}
