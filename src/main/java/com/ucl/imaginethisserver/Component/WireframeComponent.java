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
    private boolean isContainText, isContainButton, isContainTextBox,
            isContainForm, isContainSideBar, isContainImageButton,
            isContainImage, isContainChart, isContainMap, 
            isContainSwitch, isContainDropdown;

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
            }else if((component.getType().equals("RECTANGLE") || component.getType().equals("GROUP")) && component.getName().toLowerCase().contains("picture")){
                Image image;
                if(component.getType().equals("RECTANGLE")) {
                    image = ((Rectangle) component).convertToImage();
                }else{
                    image = ((Group)component).convertToImage();
                }
                frontendComponentList.add(image);
                if(!isContainImage){
                    isContainImage = true;
                }
            }else if(component.getType().equals("RECTANGLE") && component.getName().toLowerCase().contains("map")){
                Map map = ((Rectangle)component).convertToMap();
                frontendComponentList.add(map);
                if(!isContainMap){
                    isContainMap = true;
                }
            }else if(component.getName().toLowerCase().contains("switch")){
                Switch aSwitch = component.convertSwitch();
                frontendComponentList.add(aSwitch);
                if(!isContainSwitch){
                    isContainSwitch = true;
                }
            }
              // if this component is a button
              else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("textbutton")){
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
            else if(component.getType().equals("GROUP")&& component.getName().toLowerCase().contains("navigation")){
                if(NAV_BAR == null) {
                    NAV_BAR = ((Group) component).convertNavBar(projectID, accessToken, authenticateType);

                }
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
                if(!isContainText && form.isContainText()){
                    isContainText = true;
                }
                if(!isContainImageButton && form.isContainImageButton()){
                    isContainImageButton = true;
                }
                if(!isContainButton && form.isContainButton()){
                    isContainButton = true;
                }
                if(!isContainTextBox && form.isContainTextBox()){
                    isContainTextBox = true;
                }
                if(!isContainImage && form.isContainImage()){
                    isContainImage = true;
                }
                if(!isContainChart && form.isContainChart()){
                    isContainChart = true;
                }
                if(!isContainDropdown && form.isContainDropdown()){
                    isContainDropdown = true;
                }if(!isContainSwitch && form.isContainSwitch()){
                    isContainSwitch = true;
                }
                frontendComponentList.add(form);
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("slider")){
                Slider slider = ((Group) component).convertSlider();
                if(!isContainSideBar){
                    isContainSideBar = true;
                }
                frontendComponentList.add(slider);
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("imagebutton")){
                ImageButton imageButton = ((Group) component).convertImageButton(projectID,accessToken,authenticateType);
                if(!isContainImageButton){
                    isContainImageButton = true;
                }
                frontendComponentList.add(imageButton);
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("chart")){
                Chart fixedChart = ((Group) component).convertToFixedChart();
                if(!isContainChart){
                    isContainChart = true;
                }
                frontendComponentList.add(fixedChart);
            }else if(component.getType().equals("GROUP") && component.getName().toLowerCase().contains("dropdown")){
                Dropdown dropdown = ((Group) component).convertToDropdown();
                if(!isContainDropdown){
                    isContainDropdown = true;
                }
                frontendComponentList.add(dropdown);
            }
        }
    }

    public String generateImportCode() throws IOException {
       StringBuilder importCode = new StringBuilder();
       importCode.append("import { View, ScrollView");
      if(isContainImage){
          importCode.append(", Image");
      }
       importCode.append(" } from \"react-native\"\n");

       importCode.append("import React, { Component");
       importCode.append(" } from \"react\"" + "\n");

       importCode.append("import base from \"../../assets/baseStyle\"" + "\n");
       CodeGenerator.writeBaseStyleCode();
       if(isContainText || isContainChart){
           importCode.append("import P from '../reusables/P'").append("\n");
           CodeGenerator.writeReusableComponentCode(ReusableComponent.P);
       }
       if(isContainButton){
            importCode.append("import Button from '../reusables/Button'").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.BUTTON);
       }
       if(IS_CONTAIN_NAVBAR){
           importCode.append("import { StatusBar } from 'expo-status-bar'").append("\n");
       }
        if(isContainTextBox){
            importCode.append("import InputField from '../reusables/InputField'").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.INPUTFIELD);
        }

        if(isContainSideBar){
            importCode.append("import CustomSlider from \"../reusables/CustomSlider\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.SLIDER);
        }
        if(isContainImageButton){
            importCode.append("import ImageButton from \"../reusables/ImageButton\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.IMAGE_BUTTON);
        }
        if(isContainSwitch){
            importCode.append("import Toggle from \"../reusables/Toggle\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.SWITCH);
        }
        if(isContainChart){
            importCode.append("import {\n")
                    .append("  LineChart,\n")
                    .append("  BarChart,\n")
                    .append("  PieChart\n")
                    .append("} from \"react-native-chart-kit\"").append("\n");
        }
        if(isContainMap){
            importCode.append("import GoogleMap from \"../reusables/GoogleMap\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.GOOGLE_MAP);
        }
        if(isContainDropdown){
            importCode.append("import Dropdown from \"../reusables/Dropdown\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.DROPDOWN);
        }
       importCode.append("\n");

       return importCode.toString();
    }

    public String generateViewCode(String className) throws IOException {
        StringBuilder viewCode = new StringBuilder();
        viewCode.append("class ").append(className).append(" extends Component {");
        viewCode.append("render() {\n");
        if(isContainChart){
            viewCode.append(FixedChartComponent.generateCode());
        }
        viewCode.append("        return (\n" +
                "            <ScrollView style={{flex: 1, padding: 10, backgroundColor: ").append(backgroundColor.toString()).append("}}>").append("\n");

        ArrayList<List<FrontendComponent>> inlineComponentList = FrontendUtil.getInlineComponentList(frontendComponentList);
        int preY = 0;
        for(List<FrontendComponent> curList : inlineComponentList){
            //There is only one component in this line
            if(curList.size() == 1){
                int marginTop = Math.max(curList.get(0).getPositionY() - preY, 0);
                String alignCode = "";
                String align = curList.get(0).getAlign();
                switch (align){
                    case "RIGHT":
                        alignCode = "<View style={{flexDirection: \"row\", justifyContent: \"flex-end\", marginTop: " + marginTop + "}}>\n";
                        break;
                    default:
                        alignCode = "<View style={{marginTop: " + marginTop +"}}>\n";
                }
                viewCode.append(alignCode);
                viewCode.append(curList.get(0).generateCode()).append("\n");
                // If the component is align to right;
                viewCode.append("</View>\n");
                preY = curList.get(0).getPositionY() + curList.get(0).getHeight();

            }else if(curList.size() > 1){
                int minY = Integer.MAX_VALUE;
                int maxY = -1;
                for(FrontendComponent component : curList){
                   if(component.getPositionY() < minY){
                       minY = component.getPositionY();
                   }
                }
                int marginTop = Math.max(minY - preY, 0);
                viewCode.append("<View style={{flexDirection: \"row\", justifyContent: \"space-between\", marginTop: " + marginTop + "}}>" + "\n");
                for(FrontendComponent component : curList){
                    viewCode.append(component.generateCode()).append("\n");
                    if(component.getPositionY() + component.getHeight() > maxY){
                        maxY = component.getPositionY() + component.getHeight();
                    }
                }
                viewCode.append(" </View>" + "\n");
                preY = maxY;
            }
        }
        viewCode.append("            </ScrollView>\n" +
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
