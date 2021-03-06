package com.ucl.imaginethisserver.Util;

import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import com.ucl.imaginethisserver.FigmaComponents.FigmaWireframe;
import com.ucl.imaginethisserver.ReactComponents.AppJSComponent;
import com.ucl.imaginethisserver.ReactComponents.ReactComponent;
import com.ucl.imaginethisserver.ReactComponents.ReactWireframe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The CodeGenerator class is used to generate React Native code
 */
@Component
public class CodeGenerator {

    private final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    private FileUtil fileUtil;

    @Value("${config.outputStorageFolder}")
    private String outputStorageFolder;

    @Value("${config.templateFilesFolder}")
    private String templateFilesFolder;

    @Autowired
    public CodeGenerator(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }


    /**
     * This method is used to create the root folder for the current project. The default path of root folder would OutputStorage/[folderName]
     * where the default [folderName] is its project ID
     * @param figmaFile
     * @throws IOException
     */
    public void generateOutputFolder(FigmaFile figmaFile) throws IOException {
        String projectDirectory = outputStorageFolder + "/" + figmaFile.getProjectID();
        fileUtil.makeDirectory(outputStorageFolder); // Create main folder in case not already created
        fileUtil.deleteDirectory(projectDirectory);
        fileUtil.makeDirectory(projectDirectory);
        fileUtil.makeDirectory(projectDirectory + "/assets");
        fileUtil.makeDirectory(projectDirectory + "/components");
        fileUtil.makeDirectory(projectDirectory + "/components/views");
        fileUtil.makeDirectory(projectDirectory + "/components/reusables");
    }


    /**
     * This method is used to generate static files that are in all projects same
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

        // Create BaseStyle.js file
        String baseStyle = fileUtil.readFile(templateFilesFolder + "/BaseStyle.js");
        fileUtil.writeFile(projectFolder + "/assets/BaseStyle.js", baseStyle);
    };


    /**
     * This method is used for generating code of individual wireframes
     * @param figmaFile
     * @throws IOException
     */
    public void generateWireframes(FigmaFile figmaFile) throws IOException {
        String projectDirectory = outputStorageFolder + "/" + figmaFile.getProjectID();
        for (FigmaWireframe wireframe : figmaFile.getWireframes()) {
            String wireframeName = wireframe.getName();
            ReactWireframe reactWireframe = new ReactWireframe(wireframe);
            String outputCode = reactWireframe.generateCode();
            fileUtil.writeFile(projectDirectory + "/components/views/" + wireframeName + ".js", outputCode);
        }
    }

    /**
     * This method is used to generate the React Native source code for any reusable components
     * @param figmaFile The reusable component the user try to generate
     * @throws IOException
     */
    public void generateReusableComponents(FigmaFile figmaFile) throws IOException {
        // Find out all the components in the project that require reusable component
        Set<String> reusableComponents = new HashSet<>();
        for (FigmaWireframe wireframe : figmaFile.getWireframes()) {
            ReactWireframe reactWireframe = new ReactWireframe(wireframe);
            for (ReactComponent component : reactWireframe.getComponents()) {
                if (component.requiresReusableComponent()) {
                    reusableComponents.add(component.getReusableComponentName());
                }
            }
        }
        // Generate the source file for each of the reusable components
        String projectFolder = outputStorageFolder + "/" + figmaFile.getProjectID();
        Iterator<String> iterator = reusableComponents.iterator();
        while (iterator.hasNext()) {
            String reusableComponent = iterator.next();
            String reusableCode = fileUtil.readFile(templateFilesFolder + "/" + reusableComponent);
            fileUtil.writeFile(projectFolder + "/components/reusables/" + reusableComponent, reusableCode);
        }
    }


    /**
     * This method is used to generate the App.js file. It would only be called if there is a bottom navigation bar or navigators in wireframes.
     * @param figmaFile
     * @throws IOException
     */
    public void generateAppJSCode(FigmaFile figmaFile) throws IOException {
        String projectFolder = outputStorageFolder + "/" + figmaFile.getProjectID();
        String appJSCode = AppJSComponent.generateCode(figmaFile);
        fileUtil.writeFile(projectFolder + "/App.js", appJSCode);
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

    public void setFileUtil(FileUtil fileUtil) { this.fileUtil = fileUtil; }
}
