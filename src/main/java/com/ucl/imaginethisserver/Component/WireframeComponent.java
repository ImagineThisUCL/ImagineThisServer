package com.ucl.imaginethisserver.Component;

import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.DAO.*;
import com.ucl.imaginethisserver.FrontendComponent.Button;
import com.ucl.imaginethisserver.FrontendComponent.TextBox;
import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;
import com.ucl.imaginethisserver.FrontendComponent.FrontendText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WireframeComponent{
    public ArrayList<FrontendComponent> frontendComponentList = new ArrayList<>();
    private boolean isContainText, isContainButton, isContainTextBox;
    private FigmaColor backgroundColor;
    private String backgroundImage;

    public WireframeComponent(Wireframe wireframe) {
        this.backgroundColor = wireframe.getFills().get(0).getColor();
        for(FigmaComponent component : wireframe.getComponentList()){
            //If this component is a text
            if(component.getType().equals("TEXT")){
                FrontendText frontendText = ((Text)component).convertToFrontendText();
                frontendComponentList.add(frontendText);
                if(!isContainText){
                    isContainText = true;
                }
            }
            // if this component is a button
            else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("button")){

                Button button = ((Group)component).convertButton();
                frontendComponentList.add(button);
                if(!isContainButton){
                    isContainButton = true;
                }
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("textbox")){

                TextBox textBox = ((Group)component).convertTextBox();
                frontendComponentList.add(textBox);
                if(!isContainTextBox){
                    isContainTextBox = true;
                }
            }

        }
    }

    public String generateImportCode() throws IOException {
       StringBuilder importCode = new StringBuilder();
       importCode.append("import { View, ScrollView } from \"react-native\"\n" +
               "import React, { Component } from \"react\"" + "\n");
       importCode.append("import base from \"../../assets/baseStyle\"" + "\n");
       CodeGenerator.writeBaseStyleCode();
       if(isContainText){
           importCode.append("import P from '../reusables/P'" + "\n");
           CodeGenerator.writeReusableComponentCode(ReusableComponent.P);
       }
       if(isContainButton){
            importCode.append("import Button from '../reusables/Button'" + "\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.BUTTON);
       }
        if(isContainTextBox){
            importCode.append("import { Input, Card, ListItem, Icon } from 'react-native-elements';" + "\n");
        }
       importCode.append("\n");

       return importCode.toString();
    }

    public String generateViewCode(String className){
        ArrayList<List<FrontendComponent>> inlineComponentList = new ArrayList<>();
        StringBuilder viewCode = new StringBuilder();
        viewCode.append("class ").append(className).append(" extends Component {");
        viewCode.append("render() {\n" +
                "        return (\n" +
                "            <ScrollView style={{flex: 1, padding: 10, backgroundColor: "+backgroundColor.toString()+"}}>" + "\n");
//        for(FrontendComponent frontendComponent : frontendComponentList){
//            viewCode.append(frontendComponent.generateCode()).append("\n");
//        }
        FrontendComponent preComponent = frontendComponentList.get(0);
        int startIndex = 0;
        int endIndex = -1;
        for(int i = 0; i < frontendComponentList.size(); i++){
            if(!frontendComponentList.get(i).isSameLine(preComponent)){
                preComponent = frontendComponentList.get(i);
                List<FrontendComponent> list = new ArrayList<>();
                for(int t = startIndex; t <= endIndex; t++){
                    list.add(frontendComponentList.get(t));
                }
                inlineComponentList.add(list);
                startIndex = i;
                endIndex = startIndex;
            }else{
                endIndex ++;
            }
        }
        List<FrontendComponent> list = new ArrayList<>();
        for(int t = startIndex; t <= endIndex; t++){
            list.add(frontendComponentList.get(t));
        }
        inlineComponentList.add(list);
        for(List<FrontendComponent> curList : inlineComponentList){
            //There is only one component in this line
            if(curList.size() == 1){
                viewCode.append(curList.get(0).generateCode() + "\n");
            }else if(curList.size() > 1){
                viewCode.append("<View style={{flexDirection: \"row\", justifyContent: \"space-between\"}}>" + "\n");
                for(FrontendComponent component : curList){
                    viewCode.append(component.generateCode() + "\n");
                }
                viewCode.append(" </View>" + "\n");
            }
        }
        viewCode.append("  </ScrollView>\n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "export default ").append(className);

        return viewCode.toString();
    }

    public String generateCode(String className) throws IOException {
        StringBuilder code = new StringBuilder();
        code.append(this.generateImportCode()).append(this.generateViewCode(className));
        return code.toString();
    }

}
