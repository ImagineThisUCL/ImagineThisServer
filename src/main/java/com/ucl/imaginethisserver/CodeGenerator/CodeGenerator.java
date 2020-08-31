package com.ucl.imaginethisserver.CodeGenerator;

import com.ucl.imaginethisserver.Component.*;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.Util.AuthenticateType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The CodeGenerator class is used to generate React Native code
 */
public class CodeGenerator {

    /**
     * This method is used to create the root folder for the current project. The default path of root folder would OutputStorage/[folderName]
     * where the default [folderName] is its project ID
     * @param folderName The root folder name for current generating project. The default value is the [project ID]
     * @throws IOException
     */
    public static void generateOutputFolder(String folderName) throws IOException{
        File storageFolder = new File("OutputStorage");
        storageFolder.mkdir();
        File outputAppFolder = new File("OutputStorage/" + folderName);
        outputAppFolder.mkdir();
    }

    /**
     * This method is used to generate the package.json file
     * @throws IOException
     */
    public static void generatePackageFile(String folderName) throws IOException {
        String outputCode = "";
        outputCode = PackageComponent.generateCode();
        generateOutputFolder(folderName);
        File component_file = new File("OutputStorage/" + folderName + "/package.json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(component_file, false));
        writer.append(outputCode);
        writer.close();
    }

    /**
     * This method is used to generate the React Native source code for any reusable components
     * @param component The reusable component the user try to generate
     * @throws IOException
     */
    public static void writeReusableComponentCode(ReusableComponent component, String folderName) throws IOException {
        String outputCode = "";
        String fileName = "";
        switch (component){
            case P:
                outputCode = PComponent.generateCode();
                fileName = "P.js";
                break;
            case BUTTON:
                outputCode = ButtonComponent.generateCode();
                fileName = "Button.js";
                break;
            case INPUTFIELD:
                outputCode = InputFieldComponent.generateCode();
                fileName = "InputField.js";
                break;
            case SLIDER:
                outputCode = SliderComponent.generateCode();
                fileName = "CustomSlider.js";
                break;
            case IMAGE_BUTTON:
                outputCode = ImageButtonComponent.generateCode();
                fileName = "ImageButton.js";
                break;
            case GOOGLE_MAP:
                outputCode = GoogleMapComponent.generateCode();
                fileName = "GoogleMap.js";
                break;
            case SWITCH:
                outputCode = SwitchComponent.generateCode();
                fileName = "Toggle.js";
                break;
            case DROPDOWN:
                outputCode = DropdownComponent.generateCode();
                fileName = "Dropdown.js";
                break;
        }
        generateOutputFolder(folderName);
        File cfile = new File("OutputStorage/" + folderName + "/components");
        cfile.mkdir();
        File vfile = new File("OutputStorage/" + folderName + "/components/reusables");
        vfile.mkdir();
        File component_file = new File("OutputStorage/" + folderName + "/components/reusables/" + fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(component_file, false));
        writer.append(outputCode);
        writer.close();

    }

    /**
     * This method is used to generate source code for any wireframe.
     * @param wireframeName The name of current generating wireframe
     * @param wireframe the wireframe object of current generating wireframe
     * @param projectID current Figma project ID
     * @param accessToken user's access token
     * @param authenticateType the authenticate type
     * @throws IOException
     */
    public static void writeWireframeCode(String wireframeName, Wireframe wireframe, String projectID, String accessToken, AuthenticateType authenticateType, String folderName) throws IOException {
        wireframeName = wireframeName.replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ -]","");
        String outputCode = "";
        WireframeComponent wireframeComponent = new WireframeComponent(wireframe,projectID, accessToken, authenticateType);
        outputCode = wireframeComponent.generateCode(wireframeName, folderName);
        generateOutputFolder(folderName);
        File cfile = new File("OutputStorage/" + folderName + "/components");
        cfile.mkdir();
        File vfile = new File("OutputStorage/" + folderName + "/components/views");
        vfile.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter( "OutputStorage/" + folderName + "/components/views/" + wireframeName + ".js", false));
        writer.append(outputCode);
        writer.close();
    }

    /**
     * This method is used to generate source code for baseStyle.js file, which set some default style values for different components.
     * @throws IOException
     */
    public static void writeBaseStyleCode(String folderName) throws IOException {
        String outputCode = BaseStyleComponent.generateCode();
        generateOutputFolder(folderName);
        File file = new File("OutputStorage/" + folderName + "/assets");
        file.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/assets/baseStyle.js", false));
        writer.append(outputCode);
        writer.close();
    }

    /**
     * This method is used to generate the App.js file. It would only be called if there is a bottom navigation bar or navigators in wireframes.
     * @param navBar The navBar object, it is null if the all of the wireframes don't contain a navigation bottom bar.
     * @throws IOException
     */
    public static void writeAppJSCode(NavBar navBar, String folderName) throws IOException{
        String appJSCode = AppJSComponent.generateCode(navBar);
        generateOutputFolder(folderName);
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/App.js", false));
        writer.append(appJSCode);
        writer.close();

    }

    /**
     * This method is used to generate source code for placeholder.js. It would be generated when any button in a page try to navigate to a page that doesn't exist.
     * And then it would navigated to a placeholder page.
     * @throws IOException
     */
    public static void writePlaceholderCode(String folderName) throws IOException{
        String placeholderCode = PlaceholderComponent.generateCode();
        generateOutputFolder(folderName);
        File cfile = new File("OutputStorage/" + folderName + "/components");
        cfile.mkdir();
        File vfile = new File("OutputStorage/" + folderName + "/components/views");
        vfile.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/components/views/Placeholder.js", false));
        writer.append(placeholderCode);
        writer.close();
    }
}
