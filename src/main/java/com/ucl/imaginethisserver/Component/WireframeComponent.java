package com.ucl.imaginethisserver.Component;

import com.ucl.imaginethisserver.FigmaComponents.Wireframe;
import com.ucl.imaginethisserver.Util.CodeGenerator;
import com.ucl.imaginethisserver.FigmaComponents.*;
import com.ucl.imaginethisserver.FrontendComponents.*;
import com.ucl.imaginethisserver.Util.Authentication;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WireframeComponent {

    private Wireframe wireframe;
    private List<FrontendComponent> components = new ArrayList<>();


    private static boolean IS_CONTAIN_NAVBAR;
    private boolean isContainText = true, isContainButton, isContainTextBox,
            isContainForm, isContainSliderBar, isContainImageButton,
            isContainImage, isContainChart, isContainMap,
            isContainSwitch, isContainDropdown;

    private double width;
    private Color backgroundColor;
    private String backgroundImage;
    public static NavBarComponent NAV_BAR = null;

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
    public WireframeComponent(Wireframe wireframe, String projectID, Authentication auth) throws IOException {
        this.wireframe = wireframe;
        this.backgroundColor = wireframe.getBackgroundColor();
        this.width = wireframe.getAbsoluteBoundingBox().width;
        for (FigmaComponent component : wireframe.getComponents()) {
            FrontendComponent frontendComponent = component.convertToFrontendComponent();
            components.add(frontendComponent);
        }
    };


    /**
     *  Function used to combine import code with the view code
     *  to generate the content of the whole file.
     */
    public String generateCode(String className, String folderName) throws IOException {
        StringBuilder code = new StringBuilder();
        code.append(this.generateImportCode(folderName)).append(this.generateViewCode(className));
        return code.toString();
    }

    public <T extends FrontendComponent> boolean containsComponent(Class<T> cls) {
        for (FrontendComponent component : components) {
            if (cls.isInstance(component)) return true;
        }
        return false;
    };

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
        if (containsComponent(ImageComponent.class)) { importCode.append(", Image"); };
        importCode.append(" } from \"react-native\"\n");

        importCode.append("import React, { Component } from \"react\";\n");
        importCode.append("import base from \"../../assets/baseStyle\";\n");

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
        if (components.size() == 0) {
            return "";
        }
        //Put all of the components in the same line in one list
        ArrayList<List<FrontendComponent>> inlineComponentList = FrontendUtil.getInlineComponentList(components);
        int preY = 0;
        for (List<FrontendComponent> curList : inlineComponentList) {
            //There is only one component in this line
            if (curList.size() == 1) {
                int marginTop = Math.max(curList.get(0).getPositionY() - preY, 0);
                FrontendComponent curComponent = curList.get(0);
                int marginLeft = curList.get(0).getPositionX();
                int marginRight = Integer.max((int) (width - (curComponent.getPositionX() + curComponent.getWidth())), 0);
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


}
