package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.FigmaComponents.Wireframe;
import com.ucl.imaginethisserver.FrontendComponents.DropdownComponent;
import com.ucl.imaginethisserver.FrontendComponents.ImageButtonComponent;
import com.ucl.imaginethisserver.FrontendComponents.SliderComponent;
import com.ucl.imaginethisserver.FrontendComponents.SwitchComponent;
import com.ucl.imaginethisserver.Util.CodeGenerator;
import com.ucl.imaginethisserver.FigmaComponents.*;
import com.ucl.imaginethisserver.FrontendComponents.*;
import com.ucl.imaginethisserver.Util.Authentication;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class WireframeComponent {

    private Wireframe wireframe;
    private List<FrontendComponent> components = new ArrayList<>();


    private static boolean IS_CONTAIN_NAVBAR;


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
    };

    public void setComponents(List<FrontendComponent> frontendComponents) {
        components = new ArrayList<>();
        for (FrontendComponent component : frontendComponents) {
            components.add(component);
        }
    }

    public WireframeComponent(List<FrontendComponent> frontendComponents) {
        for (FrontendComponent component : frontendComponents) {
            components.add(component);
        }
    };

    /** Go through all of the direct child components of the current wireframe, convert all of the recognized components to their corresponding React Native component.
     * All of unrecognized components would be converted to an image.
     * @param wireframe
     * @throws IOException
     */
    public WireframeComponent(Wireframe wireframe) {
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
    public String generateCode(String className) throws IOException {
        StringBuilder code = new StringBuilder();
        code.append(this.generateImportCode()).append(this.generateViewCode(className));
        return code.toString();
    }

    public <T extends FrontendComponent> boolean containsComponent(Class<T> cls) {
        return FrontendComponent.containsComponent(components, cls);
    };

    /**Generate the source code of import section. Which components should be imported are determined by the included reusable components
     *import code: Regular import code that is required for every React Native page
     *+ Special import code that only shows up when the corresponding boolean is True.
     *(For example, the import image code will only be added when the isContainImage boolean
     *is turned to True.)
     * @return The source code of import section
     * @throws IOException
     */
    public String generateImportCode() throws IOException {
        StringBuilder importCode = new StringBuilder();

        importCode.append("import { View, ScrollView");
        if (containsComponent(ImageComponent.class)) { importCode.append(", Image"); };
        importCode.append(" } from 'react-native';\n");

        importCode.append("import React, { Component } from 'react';\n");
        importCode.append("import base from '../../assets/baseStyle.js';\n");

        if (containsComponent(TextComponent.class) || containsComponent(ChartComponent.class)) {
            importCode.append("import P from '../reusables/P.js';\n");
        }
        if (containsComponent(ButtonComponent.class)) {
            importCode.append("import Button from '../reusables/Button.js'\n");
        }
        if (containsComponent(NavBarComponent.class)) {
            importCode.append("import { StatusBar } from 'expo-status-bar';\n");
        }
        if (containsComponent(TextBoxComponent.class)) {
            importCode.append("import InputField from '../reusables/InputField.js';\n");
        }
        if (containsComponent(SliderComponent.class)) {
            importCode.append("import CustomSlider from '../reusables/CustomSlider.js';\n");
        }
        if (containsComponent(ImageButtonComponent.class)) {
            importCode.append("import ImageButton from '../reusables/ImageButton.js';\n");
        }
        if (containsComponent(SwitchComponent.class)) {
            importCode.append("import Toggle from '../reusables/Toggle.js';\n");
        }
        if (containsComponent(ChartComponent.class)) {
            importCode.append("import { LineChart, BarChart, PieChart } from 'react-native-chart-kit';\n");
            importCode.append("import { LINE_CHART_DATA, LINE_CHART_CONFIG } from 'Chart.js';\n");
        }
        if (containsComponent(MapComponent.class)) {
            importCode.append("import GoogleMap from '../reusables/GoogleMap.js';\n");
        }
        if (containsComponent(DropdownComponent.class)) {
            importCode.append("import Dropdown from '../reusables/Dropdown.js';\n");
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
    public String generateViewCode(String className) {
        StringBuilder viewCode = new StringBuilder();
        viewCode.append("class ").append(className).append(" extends Component {");
        viewCode.append("render() {\n");
        viewCode.append("        return (\n" +
                "            <ScrollView style={{flex: 1, padding: 0, backgroundColor: ").append(backgroundColor.toString()).append("}}>\n");
        if (components.size() == 0) { return ""; };
        // Put all of the components in the same line in one list
        List<List<FrontendComponent>> inlineComponentList = FrontendComponent.getInlineComponentList(components);
        int preY = 0;
        for (List<FrontendComponent> line : inlineComponentList) {
            // There is only one component in this line
            if (line.size() == 1) {
                FrontendComponent component = line.get(0);
                int marginTop = Math.max(component.getPositionY() - preY, 0);
                int marginLeft = component.getPositionX();
                int marginRight = Integer.max((int) (width - (component.getPositionX() + component.getWidth())), 0);
                viewCode.append("<View style={{marginTop: " + marginTop + ",marginLeft: " + marginLeft + ", marginRight: " + marginRight + "}}>\n");
                viewCode.append(component.generateCode()).append("\n");
                viewCode.append("</View>\n");
                preY = component.getPositionY() + component.getHeight();
            }

            // If there are multiple components in this line, then align these content using 'space-between'
            else if (line.size() > 1) {
                int minY = Integer.MAX_VALUE;
                int maxY = -1;
                for (FrontendComponent component : line) {
                    if (component.getPositionY() < minY) {
                        minY = component.getPositionY();
                    }
                }
                int marginTop = Math.max(minY - preY, 0);
                viewCode.append("<View style={{flexDirection: \"row\", justifyContent: \"space-between\", marginTop: " + marginTop + "}}>" + "\n");
                for (FrontendComponent component : line) {
                    viewCode.append(component.generateCode()).append("\n");
                    if (component.getPositionY() + component.getHeight() > maxY) {
                        maxY = component.getPositionY() + component.getHeight();
                    }
                }
                viewCode.append("</View>\n");
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
