package com.ucl.imaginethisserver.CodeGenerator;

import com.ucl.imaginethisserver.Component.PComponent;
import com.ucl.imaginethisserver.Component.ReusableComponent;
import com.ucl.imaginethisserver.Component.WireframeComponent;
import com.ucl.imaginethisserver.DAO.Wireframe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {
    public static void writeReusableComponentCode(ReusableComponent component) throws IOException {
        String outputCode = "";
        switch (component){
            case P:
                outputCode = PComponent.generateCode();
                break;
        }
        File file = new File("reusable");
        file.mkdir();
        File component_file = new File("reusable/P.js");
        BufferedWriter writer = new BufferedWriter(new FileWriter(component_file, false));
        writer.append(outputCode);
        writer.close();

    }

    public static void writeWireframeCode(String wireframeName, Wireframe wireframe) throws IOException {
        wireframeName = wireframeName.replaceAll(" ","");
        String outputCode = "";
        WireframeComponent wireframeComponent = new WireframeComponent(wireframe);
        outputCode = wireframeComponent.generateCode(wireframeName);
        File file = new File("views");
        file.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter("views/" + wireframeName + ".js", true));
        writer.append(outputCode);
        writer.close();
    }
}
