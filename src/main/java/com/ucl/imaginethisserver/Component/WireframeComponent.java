package com.ucl.imaginethisserver.Component;

import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.DAO.*;
import com.ucl.imaginethisserver.FrontendComponent.*;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WireframeComponent {
    public ArrayList<FrontendComponent> frontendComponentList = new ArrayList<>();
    private static boolean IS_CONTAIN_NAVBAR;
    private boolean isContainText = true, isContainButton, isContainTextBox,
            isContainForm, isContainSliderBar, isContainImageButton,
            isContainImage, isContainChart, isContainMap,
            isContainSwitch, isContainDropdown;
    private double wWidth;

    private FigmaColor backgroundColor;
    private String backgroundImage;
    public static NavBar NAV_BAR = null;

    /**
     *  Return boolean that represents whether a page contains a bottom navigation bar or not.
    */
    public static boolean IsContainNavBar() {
        return IS_CONTAIN_NAVBAR;
    }

    /**
     *  Set or change the boolean isContainNavbar of the page.
     */
    public static void setIsContainNavbar(boolean isContainNavbar) {
        IS_CONTAIN_NAVBAR = isContainNavbar;
    }

    /** Go through all of the direct child components of the current wireframe, convert all of the recognized components to their corresponding React Native component.
     * All of unrecognized components would be converted to an image.
     * @param wireframe
     * @throws IOException
     */
    public WireframeComponent(Wireframe wireframe, String projectID, String accessToken, AuthenticateType authenticateType) throws IOException {
        this.backgroundColor = wireframe.getFills().get(0).getColor();
        this.wWidth = wireframe.getAbsoluteBoundingBox().width;
        for (FigmaComponent component : wireframe.getComponentList()) {
            String compType = component.getType();
            String compName = component.getName().toLowerCase();
            //If this component is a text
            if (compType.equals("TEXT")) {
                FrontendText frontendText = ((Text) component).convertToFrontendText();
                frontendComponentList.add(frontendText);
            } else if (
                (compType.equals("RECTANGLE") || compType.equals("GROUP")) &&
                ((compName.contains("image") && !(compName.contains("imagebutton"))) ||
                compName.contains("picture") ||
                compName.contains("icon"))) {
                Image image;
                image = component.convertToImage();

                frontendComponentList.add(image);
                if (!isContainImage) {
                    isContainImage = true;
                }
            } else if (compType.equals("RECTANGLE") && compName.contains("map")) {
                Map map = ((Rectangle) component).convertToMap();
                frontendComponentList.add(map);
                if (!isContainMap) {
                    isContainMap = true;
                }
            } else if (compName.contains("switch")) {
                Switch aSwitch = component.convertSwitch();
                frontendComponentList.add(aSwitch);
                if (!isContainSwitch) {
                    isContainSwitch = true;
                }
            }
            // if this component is a button
            else if (compType.equals("GROUP") && compName.contains("textbutton")) {
                Button button = ((Group) component).convertButton();
                frontendComponentList.add(button);
                if (!isContainButton) {
                    isContainButton = true;
                }
            } else if (compType.equals("GROUP") && compName.contains("input")) {
                TextBox textBox = ((Group) component).convertTextBox();
                frontendComponentList.add(textBox);
                if (!isContainTextBox) {
                    isContainTextBox = true;
                }
            } else if (compType.equals("GROUP") && compName.contains("navigation")) {
                if (NAV_BAR == null) {
                    NAV_BAR = ((Group) component).convertNavBar(projectID, accessToken, authenticateType);
                }
                for (String navText : NavBar.BUTTON_MAP.keySet()) {
                    if (NavBar.BUTTON_MAP.get(navText).equals("Placeholder")) {
                        NavBar.BUTTON_MAP.put(navText, wireframe.getName());
                    }
                }
                if (!IS_CONTAIN_NAVBAR) {
                    IS_CONTAIN_NAVBAR = true;
                }
            } else if (compType.equals("GROUP") && ( compName.contains("form") || compName.contains("card"))) {
                Form form = ((Group) component).convertForm(projectID, accessToken, authenticateType);
                if (!isContainForm) {
                    isContainForm = true;
                }
                if (!isContainText && form.isContainText()) {
                    isContainText = true;
                }
                if (!isContainImageButton && form.isContainImageButton()) {
                    isContainImageButton = true;
                }
                if (!isContainButton && form.isContainButton()) {
                    isContainButton = true;
                }
                if (!isContainTextBox && form.isContainTextBox()) {
                    isContainTextBox = true;
                }
                if (!isContainImage && form.isContainImage()) {
                    isContainImage = true;
                }
                if (!isContainChart && form.isContainChart()) {
                    isContainChart = true;
                }
                if (!isContainDropdown && form.isContainDropdown()) {
                    isContainDropdown = true;
                }
                if (!isContainSwitch && form.isContainSwitch()) {
                    isContainSwitch = true;
                }
                if(!isContainSliderBar && form.isContainSlider()){
                    isContainSliderBar = true;
                }
                frontendComponentList.add(form);
            } else if (compType.equals("GROUP") && compName.contains("slider")) {
                Slider slider = ((Group) component).convertSlider();
                if (!isContainSliderBar) {
                    isContainSliderBar = true;
                }
                frontendComponentList.add(slider);
            } else if (compType.equals("GROUP") && compName.contains("imagebutton")) {
                ImageButton imageButton = ((Group) component).convertImageButton(projectID, accessToken, authenticateType);
                if (!isContainImageButton) {
                    isContainImageButton = true;
                }
                frontendComponentList.add(imageButton);
            } else if (compType.equals("GROUP") && compName.contains("chart")) {
                Chart fixedChart = ((Group) component).convertToFixedChart();
                if (!isContainChart) {
                    isContainChart = true;
                }
                frontendComponentList.add(fixedChart);
            } else if (compType.equals("GROUP") && compName.contains("dropdown")) {
                Dropdown dropdown = ((Group) component).convertToDropdown();
                if (!isContainDropdown) {
                    isContainDropdown = true;
                }
                frontendComponentList.add(dropdown);
            }else{
                Image image = component.convertToImage();
                frontendComponentList.add(image);
                if (!isContainImage) {
                    isContainImage = true;
                }
            }
        }
    }

    /**Generate the source code of import section. Which components should be imported are determined by the included reusable components
     *import code: Regular import code that is required for every React Native page
     *+ Special import code that only shows up when the corresponding boolean is True.
     *(For example, the import image code will only be added when the isContainImage boolean
     *is turned to True.)
     * @return The source code of import section
     * @throws IOException
     */
    public String generateImportCode(String folderName) throws IOException {
        StringBuilder importCode = new StringBuilder();
        importCode.append("import { View, ScrollView");
        if (isContainImage) {
            importCode.append(", Image");
        }
        importCode.append(" } from \"react-native\"\n");

        importCode.append("import React, { Component");
        importCode.append(" } from \"react\"" + "\n");

        importCode.append("import base from \"../../assets/baseStyle\"" + "\n");
        CodeGenerator.writeBaseStyleCode(folderName);
        if (isContainText || isContainChart) {
            importCode.append("import P from '../reusables/P'").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.P, folderName);
        }
        if (isContainButton) {
            importCode.append("import Button from '../reusables/Button'").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.BUTTON, folderName);
        }
        if (IS_CONTAIN_NAVBAR) {
            importCode.append("import { StatusBar } from 'expo-status-bar'").append("\n");
        }
        if (isContainTextBox) {
            importCode.append("import InputField from '../reusables/InputField'").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.INPUTFIELD, folderName);
        }

        if (isContainSliderBar) {
            importCode.append("import CustomSlider from \"../reusables/CustomSlider\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.SLIDER, folderName);
        }
        if (isContainImageButton) {
            importCode.append("import ImageButton from \"../reusables/ImageButton\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.IMAGE_BUTTON, folderName);
        }
        if (isContainSwitch) {
            importCode.append("import Toggle from \"../reusables/Toggle\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.SWITCH, folderName);
        }
        if (isContainChart) {
            importCode.append("import {\n")
                    .append("  LineChart,\n")
                    .append("  BarChart,\n")
                    .append("  PieChart\n")
                    .append("} from \"react-native-chart-kit\"").append("\n");
        }
        if (isContainMap) {
            importCode.append("import GoogleMap from \"../reusables/GoogleMap\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.GOOGLE_MAP, folderName);
        }
        if (isContainDropdown) {
            importCode.append("import Dropdown from \"../reusables/Dropdown\"").append("\n");
            CodeGenerator.writeReusableComponentCode(ReusableComponent.DROPDOWN, folderName);
        }
        importCode.append("\n");

        return importCode.toString();
    }

    /**
     *  View code generation, this function handles the <View> Tag wrapping of the code,
     *  the function generates code that works like a template and
     *  calling the corresponding generateCode function to generate unique code that
     *  is converted from data passed from Figma API.
     */
    public String generateViewCode(String className) throws IOException {
        StringBuilder viewCode = new StringBuilder();
        viewCode.append("class ").append(className).append(" extends Component {");
        viewCode.append("render() {\n");
        if (isContainChart) {
            viewCode.append(FixedChartComponent.generateCode());
        }
        viewCode.append("        return (\n" +
                "            <ScrollView style={{flex: 1, padding: 0, backgroundColor: ").append(backgroundColor.toString()).append("}}>").append("\n");
        if (frontendComponentList.size() == 0) {
            return "";
        }
        //Put all of the components in the same line in one list
        ArrayList<List<FrontendComponent>> inlineComponentList = FrontendUtil.getInlineComponentList(frontendComponentList);
        int preY = 0;
        for (List<FrontendComponent> curList : inlineComponentList) {
            //There is only one component in this line
            if (curList.size() == 1) {
                int marginTop = Math.max(curList.get(0).getPositionY() - preY, 0);
                FrontendComponent curComponent = curList.get(0);
                int marginLeft = curList.get(0).getPositionX();
                int marginRight = Integer.max((int) (wWidth - (curComponent.getPositionX() + curComponent.getWidth())), 0);
                String alignCode = "<View style={{marginTop: " + marginTop + ",marginLeft: " + marginLeft + ", marginRight: " + marginRight + "}}>\n";
                viewCode.append(alignCode);
                viewCode.append(curList.get(0).generateCode()).append("\n");
                // If the component is align to right;
                viewCode.append("</View>\n");
                preY = curList.get(0).getPositionY() + curList.get(0).getHeight();

            }
            // If there are multiple components in this line, then align these content using 'space-between'
            else if (curList.size() > 1) {
                int minY = Integer.MAX_VALUE;
                int maxY = -1;
                for (FrontendComponent component : curList) {
                    if (component.getPositionY() < minY) {
                        minY = component.getPositionY();
                    }
                }
                int marginTop = Math.max(minY - preY, 0);
                viewCode.append("<View style={{flexDirection: \"row\", justifyContent: \"space-between\", marginTop: " + marginTop + "}}>" + "\n");
                for (FrontendComponent component : curList) {
                    viewCode.append(component.generateCode()).append("\n");
                    if (component.getPositionY() + component.getHeight() > maxY) {
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

    /**
     *  Function used to combine import code with the view code
     *  to generate the content of the whole file.
     */
    public String generateCode(String className, String folderName) throws IOException {
        StringBuilder code = new StringBuilder();
        code.append(this.generateImportCode(folderName)).append(this.generateViewCode(className));
        return code.toString();
    }

}
