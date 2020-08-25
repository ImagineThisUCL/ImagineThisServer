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

    public static void generateOutputFolder(String folderName) throws IOException{
        File storageFolder = new File("OutputStorage");
        storageFolder.mkdir();
        File outputAppFolder = new File("OutputStorage/" + folderName);
        outputAppFolder.mkdir();
    }

    public static void generatePackageFile(String folderName) throws IOException {
        String outputCode = "";
        outputCode = PackageComponent.generateCode();
        generateOutputFolder(folderName);
        File component_file = new File("OutputStorage/" + folderName + "/package.json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(component_file, false));
        writer.append(outputCode);
        writer.close();
    }

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

    public static void writeBaseStyleCode(String folderName) throws IOException {
        String outputCode = BaseStyleComponent.generateCode();
        generateOutputFolder(folderName);
        File file = new File("OutputStorage/" + folderName + "/assets");
        file.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/assets/baseStyle.js", false));
        writer.append(outputCode);
        writer.close();
    }

    public static void writeAppJSCode(NavBar navBar, String folderName) throws IOException{
        String appJSCode = AppJSComponent.generateCode(navBar);
        generateOutputFolder(folderName);
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputStorage/" + folderName + "/App.js", false));
        writer.append(appJSCode);
        writer.close();

    }

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
