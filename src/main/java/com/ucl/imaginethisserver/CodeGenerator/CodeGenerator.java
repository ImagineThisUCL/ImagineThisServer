package com.ucl.imaginethisserver.CodeGenerator;

import com.ucl.imaginethisserver.Component.*;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.FrontendComponent.ImageButton;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.Util.AuthenticateType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {

    public static void generateOutputFolder() throws IOException{
        File outputAppFolder = new File("OutputApp");
        outputAppFolder.mkdir();
    }

    public static void generatePackageFile() throws IOException {
        String outputCode = "";
        outputCode = PackageComponent.generateCode();
        generateOutputFolder();
        File component_file = new File("OutputApp/package.json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(component_file, false));
        writer.append(outputCode);
        writer.close();
    }

    public static void writeReusableComponentCode(ReusableComponent component) throws IOException {
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
            case SWITCH:
                outputCode = SwitchComponent.generateCode();
                fileName = "Toggle.js";
                break;
            case DROPDOWN:
                outputCode = DropdownComponent.generateCode();
                fileName = "Dropdown.js";
                break;

        }
        generateOutputFolder();
        File cfile = new File("OutputApp/components");
        cfile.mkdir();
        File vfile = new File("OutputApp/components/reusables");
        vfile.mkdir();
        File component_file = new File("OutputApp/components/reusables/" + fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(component_file, false));
        writer.append(outputCode);
        writer.close();

    }

    public static void writeWireframeCode(String wireframeName, Wireframe wireframe, String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
        wireframeName = wireframeName.replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ ]","");
        String outputCode = "";
        WireframeComponent wireframeComponent = new WireframeComponent(wireframe,projectID, accessToken, authenticateType);
        outputCode = wireframeComponent.generateCode(wireframeName);
        generateOutputFolder();
        File cfile = new File("OutputApp/components");
        cfile.mkdir();
        File vfile = new File("OutputApp/components/views");
        vfile.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputApp/components/views/" + wireframeName + ".js", false));
        writer.append(outputCode);
        writer.close();
    }

    public static void writeBaseStyleCode() throws IOException {
        String outputCode = BaseStyleComponent.generateCode();
        generateOutputFolder();
        File file = new File("OutputApp/assets");
        file.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputApp/assets/baseStyle.js", false));
        writer.append(outputCode);
        writer.close();
    }

    public static void writeAppJSCode(NavBar navBar) throws IOException{
        String appJSCode = AppJSComponent.generateCode(navBar);
        generateOutputFolder();
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputApp/App.js", false));
        writer.append(appJSCode);
        writer.close();

    }

    public static void writePlaceholderCode() throws IOException{
        String placeholderCode = PlaceholderComponent.generateCode();
        generateOutputFolder();
        File cfile = new File("OutputApp/components");
        cfile.mkdir();
        File vfile = new File("OutputApp/components/views");
        vfile.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("OutputApp/components/views/Placeholder.js", false));
        writer.append(placeholderCode);
        writer.close();
    }
}
