package com.ucl.imaginethisserver.Util;

import com.ucl.imaginethisserver.FigmaComponents.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @Value("${config.outputStorageFolder}")
    private String OUTPUT_STORAGE_FOLDER;

    static final String TEST_PROJECT_ID = "testId";
    static FigmaFile testFigmaFile;

    @BeforeEach
    void setUpMocks() throws IOException {
        // Initialize new empty FigmaFile
        testFigmaFile = new FigmaFile(TEST_PROJECT_ID);
        // Mock readFile method, because some operations can be performed with return strings
        when(testFileUtil.readFile(any())).thenReturn("");
    }

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectFoldersAreCreated() throws IOException {
        testCodeGenerator.generateOutputFolder(testFigmaFile);

        // Verify output folder is firstly delete, and then correct folders are created
        String projectFolder = String.format("%s/%s", OUTPUT_STORAGE_FOLDER, TEST_PROJECT_ID);
        verify(testFileUtil).deleteDirectory(eq(projectFolder));
        verify(testFileUtil).makeDirectory(eq(OUTPUT_STORAGE_FOLDER));
        verify(testFileUtil).makeDirectory(eq(projectFolder));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/assets"));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/components"));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/components/views"));
        verify(testFileUtil).makeDirectory(eq(projectFolder + "/components/reusables"));
    }

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectPackageFilesAreCreated() throws IOException {
        testCodeGenerator.generatePackageFiles(testFigmaFile);

        String projectFolder = String.format("%s/%s", OUTPUT_STORAGE_FOLDER, TEST_PROJECT_ID);
        // Verify that correct default files are generated
        verify(testFileUtil).writeFile(eq(projectFolder + "/package.json"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/app.config.js"), any());
        verify(testFileUtil).writeFile(eq(projectFolder + "/assets/BaseStyle.js"), any());
    }

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectReusableFilesAreCreated() throws IOException {
        FigmaPage testPage = new FigmaPage();
        FigmaWireframe testWireframe = new FigmaWireframe();
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

        String reusablesFolder = String.format("%s/%s/components/reusables", OUTPUT_STORAGE_FOLDER, TEST_PROJECT_ID);
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

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectAppJSCreated() throws IOException {
        testCodeGenerator.generateAppJSCode(testFigmaFile);
        String projectFolder = String.format("%s/%s", OUTPUT_STORAGE_FOLDER, TEST_PROJECT_ID);
        // Verify that correct App.js file is generated
        verify(testFileUtil).writeFile(eq(projectFolder + "/App.js"), any());
    }


}
