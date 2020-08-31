package com.ucl.imaginethisserver.CodeGenerator;

import com.ucl.imaginethisserver.Component.*;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.Util.AuthenticateType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {

    /**
     *  Function used to generate Output folder according to the standard structure of
     *  a React Native app.
    */
    public static void generateOutputFolder(String folderName) throws IOException{
        File storageFolder = new File("OutputStorage");
        storageFolder.mkdir();
        File outputAppFolder = new File("OutputStorage/" + folderName);
        outputAppFolder.mkdir();
    }

    /**
     * Function used to create and write a fixed package.json file that is required when
     * running the application
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
     * Function used to generate fixed reusable codes into the reusable folder,
     * the code changes according to the accepted name.
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
     *  Function used to generate view files (files used to show the actual pages within the app).
     *  The function accepts a name and are called every time a new page view file is required.
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
     *  Function used to create fixed style code file baseStyle.js within the assets folder.
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
     *  Function used to create App.js under the root folder,
     *  code of App.js can be created by calling generateCode function of the navBar.
     */
    public static void writeAppJSCode(NavBar navBar, String folderName) throws IOException{
        String appJSCode = AppJSComponent.generateCode(navBar);
        generateOutputFolder(folderName);
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/App.js", false));
        writer.append(appJSCode);
        writer.close();

    }

    /**
     *  Function used to create a page called Placeholder.js
     *  in order to catch links that redirects to pages that does not exist or
     *  pages that is not generated. Basically a blank page.
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
