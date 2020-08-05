package com.ucl.imaginethisserver.Component;

import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.DAO.*;
import com.ucl.imaginethisserver.FrontendComponent.*;
import com.ucl.imaginethisserver.Util.FrontendUtil;
import com.ucl.imaginethisserver.Util.AuthenticateType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WireframeComponent{
    public ArrayList<FrontendComponent> frontendComponentList = new ArrayList<>();
    private static boolean IS_CONTAIN_NAVBAR;
    private boolean isContainText, isContainButton, isContainTextBox, isContainForm, isContainSideBar;
    private FigmaColor backgroundColor;
    private String backgroundImage;
    public static NavBar NAV_BAR = null;

    public static boolean IsContainNavBar(){
        return IS_CONTAIN_NAVBAR;
    }

    public WireframeComponent(Wireframe wireframe, String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
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
            else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("navigation")){
                NAV_BAR = ((Group)component).convertNavBar(projectID, accessToken, authenticateType);
                for(String navText : NavBar.BUTTON_MAP.keySet()){
                    if(NavBar.BUTTON_MAP.get(navText).equals("Placeholder")){
                        NavBar.BUTTON_MAP.put(navText,wireframe.getName());
                        break;
                    }
                }
                if(!IS_CONTAIN_NAVBAR){
                    IS_CONTAIN_NAVBAR = true;
                }
            }
            else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("form")){
                Form form = ((Group) component).convertForm(projectID, accessToken, authenticateType);
                if(!isContainForm){
                    isContainForm = true;
                }
                frontendComponentList.add(form);
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("slider")){
                Slider slider = ((Group) component).convertSlider();
                if(!isContainSideBar){
                    isContainSideBar = true;
                }
                frontendComponentList.add(slider);
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("iconbutton")){

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
       if(IS_CONTAIN_NAVBAR){
           importCode.append("import { StatusBar } from 'expo-status-bar'");
       }
        if(isContainTextBox){
            importCode.append("import InputField from '../reusables/InputField'" + "\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.INPUTFIELD);
        }

        if(isContainSideBar){
            importCode.append("import CustomSlider from \"../reusables/CustomSlider\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.SLIDER);
        }
       importCode.append("\n");

       return importCode.toString();
    }

    public String generateViewCode(String className){
        StringBuilder viewCode = new StringBuilder();
        viewCode.append("class ").append(className).append(" extends Component {");
        viewCode.append("render() {\n" +
                "        return (\n" +
                "            <ScrollView style={{flex: 1, padding: 10, backgroundColor: "+backgroundColor.toString()+"}}>" + "\n");

        ArrayList<List<FrontendComponent>> inlineComponentList = FrontendUtil.getInlineComponentList(frontendComponentList);
        for(List<FrontendComponent> curList : inlineComponentList){
            //There is only one component in this line
            if(curList.size() == 1){
                String alignCode = "";
                String align = curList.get(0).getAlign();
                switch (align){
                    case "RIGHT":
                        alignCode = "<View style={{flexDirection: \"row\", justifyContent: \"flex-end\"}}>\n";
                        break;
                    default:
                        alignCode = "";
                }
                viewCode.append(alignCode);
                viewCode.append(curList.get(0).generateCode()).append("\n");
                // If the component is align to right;
                if(alignCode.length() > 0){
                    viewCode.append("</View>\n");
                }

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
