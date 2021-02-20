package com.ucl.imaginethisserver.Util;

import com.ucl.imaginethisserver.Component.*;
import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import com.ucl.imaginethisserver.FigmaComponents.Wireframe;
import com.ucl.imaginethisserver.FrontendComponents.AppJSComponent;
import com.ucl.imaginethisserver.FrontendComponents.NavBarComponent;
import com.ucl.imaginethisserver.FrontendComponents.WireframeComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The CodeGenerator class is used to generate React Native code
 */
@Component
public class CodeGenerator {

    private final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    private FileUtil fileUtil;

    @Value("config.outputStorageFolder")
    private String outputStorageFolder;

    @Value("config.templateFilesFolder")
    private String templateFilesFolder;

    @Autowired
    public CodeGenerator(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public String getOutputStorageFolder() { return outputStorageFolder; };
    public String getTemplateFilesFolder() { return templateFilesFolder; };
    public void setOutputStorageFolder(String folder) { outputStorageFolder = folder; };
    public void setTemplateFilesFolder(String folder) { templateFilesFolder = folder; };

    /**
     * This method is used to create the root folder for the current project. The default path of root folder would OutputStorage/[folderName]
     * where the default [folderName] is its project ID
     * @param figmaFile
     * @throws IOException
     */
    public void generateOutputFolder(FigmaFile figmaFile) {
        String projectDirectory = outputStorageFolder + "/" + figmaFile.getProjectID();
        fileUtil.makeDirectory(outputStorageFolder);
        fileUtil.makeDirectory(projectDirectory);
        fileUtil.makeDirectory(projectDirectory + "/components");
        fileUtil.makeDirectory(projectDirectory + "/components/views");
    }



    /**
     * This method is used to generate the package.json file
     * @throws IOException
     */
    public void generatePackageFiles(FigmaFile figmaFile) throws IOException {
        String projectFolder = outputStorageFolder + "/" + figmaFile.getProjectID();

        // Create package.json file
        String packageJson = fileUtil.readFile(templateFilesFolder + "/package.json");
        fileUtil.writeFile(projectFolder + "/package.json", packageJson);

        // Create app.config.js file
        String appJson = fileUtil.readFile(templateFilesFolder + "/app.config.js");
        fileUtil.writeFile(projectFolder + "/app.config.js", appJson);
    };


    /**
     * This method is used for generating code of individual wireframes
     * @param figmaFile
     * @throws IOException
     */
    public void generateWireframes(FigmaFile figmaFile) throws IOException {

        String outputDirectory = outputStorageFolder + "/" + figmaFile.getProjectName();

        for (Wireframe wireframe : figmaFile.getWireframes()) {
            String wireframeName = wireframe.getName();
            WireframeComponent wireframeComponent = new WireframeComponent(wireframe);
            String outputCode = wireframeComponent.generateCode();
            fileUtil.writeFile(outputDirectory + "/components/views/" + wireframeName + ".js", outputCode);
        }
    }

    /**
     * This method is used to generate the React Native source code for any reusable components
     * @param component The reusable component the user try to generate
     * @throws IOException
     */
    public void writeReusableComponentCode(FigmaFile figmaFile, ReusableComponent component) throws IOException {
        String outputCode = "";
        String fileName = "";
//        switch (component){
//            case P:
//                outputCode = PComponent.generateCode();
//                fileName = "P.js";
//                break;
//            case BUTTON:
//                outputCode = ButtonComponent.generateCode();
//                fileName = "Button.js";
//                break;
//            case INPUTFIELD:
//                outputCode = InputFieldComponent.generateCode();
//                fileName = "InputField.js";
//                break;
//            case SLIDER:
//                outputCode = SliderComponent.generateCode();
//                fileName = "CustomSlider.js";
//                break;
//            case IMAGE_BUTTON:
//                outputCode = ImageButtonComponent.generateCode();
//                fileName = "ImageButton.js";
//                break;
//            case GOOGLE_MAP:
//                outputCode = GoogleMapComponent.generateCode();
//                fileName = "GoogleMap.js";
//                break;
//            case SWITCH:
//                outputCode = SwitchComponent.generateCode();
//                fileName = "Toggle.js";
//                break;
//            case DROPDOWN:
//                outputCode = DropdownComponent.generateCode();
//                fileName = "Dropdown.js";
//                break;
//        }
//        makeDirectory("OutputStorage/" + figmaFile.getProjectID() + "/components");
//        makeDirectory("OutputStorage/" + figmaFile.getProjectID() + "/components/reusables");
//        File component_file = new File("OutputStorage/" + figmaFile.getProjectID() + "/components/reusables/" + fileName);
//        BufferedWriter writer = new BufferedWriter(new FileWriter(component_file, false));
//        writer.append(outputCode);
//        writer.close();

    }

    /**
     * This method is used to generate source code for baseStyle.js file, which set some default style values for different components.
     * @throws IOException
     */
    public static void writeBaseStyleCode(String folderName) throws IOException {
//        String outputCode = BaseStyleComponent.generateCode();
//        File file = new File("OutputStorage/" + folderName + "/assets");
//        file.mkdir();
//        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/assets/baseStyle.js", false));
//        writer.append(outputCode);
//        writer.close();
    }

    /**
     * This method is used to generate the App.js file. It would only be called if there is a bottom navigation bar or navigators in wireframes.
     * @param navBarComponent The navBar object, it is null if the all of the wireframes don't contain a navigation bottom bar.
     * @throws IOException
     */
    public static void writeAppJSCode(NavBarComponent navBarComponent, String folderName) throws IOException{
//        String appJSCode = AppJSComponent.generateCode(navBarComponent);
//        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/App.js", false));
//        writer.append(appJSCode);
//        writer.close();

    }

    /**
     * This method is used to generate source code for placeholder.js. It would be generated when any button in a page try to navigate to a page that doesn't exist.
     * And then it would navigated to a placeholder page.
     * @throws IOException
     */
    public static void writePlaceholderCode(String folderName) throws IOException{
//        String placeholderCode = PlaceholderComponent.generateCode();
//        File cfile = new File("OutputStorage/" + folderName + "/components");
//        cfile.mkdir();
//        File vfile = new File("OutputStorage/" + folderName + "/components/views");
//        vfile.mkdir();
//        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/components/views/Placeholder.js", false));
//        writer.append(placeholderCode);
//        writer.close();
    }
}
