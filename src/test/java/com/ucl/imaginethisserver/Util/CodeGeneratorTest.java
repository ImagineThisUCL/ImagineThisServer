package com.ucl.imaginethisserver.Util;

import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CodeGeneratorTest {

    static CodeGenerator testCodeGenerator;

    static final String TEST_PROJECT_ID = "testId";
    static final FigmaFile testFigmaFile = new FigmaFile(TEST_PROJECT_ID);

    @Value("config.outputStorageFolder")
    private String outputStorageFolder;

    @BeforeAll
    static void setUpMocks() {
        testCodeGenerator = mock(CodeGenerator.class);
    }

    @Test
    void givenCorrectFigmaFile_whenGeneratingCode_thenCorrectPackageFilesAreCreated() throws IOException {

        doCallRealMethod().when(testCodeGenerator).generatePackageFiles(testFigmaFile);
        String correctPackageJsonFile = outputStorageFolder + "/" + TEST_PROJECT_ID + "/package.json";
        String correctAppConfigFile = outputStorageFolder + "/" + TEST_PROJECT_ID + "/app.config.js";

        // Make sure that the method writes package.json and app.config.js files
        verify(testCodeGenerator).writeFile(correctPackageJsonFile, anyString());
        verify(testCodeGenerator).writeFile(correctAppConfigFile, anyString());

    }

}
