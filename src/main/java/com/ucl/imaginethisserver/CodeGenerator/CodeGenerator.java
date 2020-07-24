package com.ucl.imaginethisserver.CodeGenerator;

import com.ucl.imaginethisserver.Component.*;
import com.ucl.imaginethisserver.DAO.Wireframe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {
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
        }
        File cfile = new File("components");
        cfile.mkdir();
        File vfile = new File("components/reusables");
        vfile.mkdir();
        File component_file = new File("components/reusables/" + fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(component_file, false));
        writer.append(outputCode);
        writer.close();

    }

    public static void writeWireframeCode(String wireframeName, Wireframe wireframe) throws IOException {
        wireframeName = wireframeName.replaceAll(" ","");
        String outputCode = "";
        WireframeComponent wireframeComponent = new WireframeComponent(wireframe);
        outputCode = wireframeComponent.generateCode(wireframeName);
        File cfile = new File("components");
        cfile.mkdir();
        File vfile = new File("components/views");
        vfile.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("components/views/" + wireframeName + ".js", true));
        writer.append(outputCode);
        writer.close();
    }

    public static void writeBaseStyleCode() throws IOException {
        String outputCode = BaseStyleComponent.generateCode();
        File file = new File("assets");
        file.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("assets/baseStyle.js",false));
        writer.append(outputCode);
        writer.close();
    }
}
