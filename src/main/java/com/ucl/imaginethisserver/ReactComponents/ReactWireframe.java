package com.ucl.imaginethisserver.ReactComponents;

import com.ucl.imaginethisserver.FigmaComponents.FigmaWireframe;
import com.ucl.imaginethisserver.FigmaComponents.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReactWireframe {

    private FigmaWireframe wireframe;
    private List<ReactComponent> components = new ArrayList<>();

    private Color backgroundColor;
    private AbsoluteBoundingBox absoluteBoundingBox;

    public List<ReactComponent> getComponents() { return components; }

    public void setComponents(List<ReactComponent> reactComponents) {
        components = new ArrayList<>();
        for (ReactComponent component : reactComponents) {
            components.add(component);
        }
    }

    public ReactWireframe(List<ReactComponent> reactComponents) {
        for (ReactComponent component : reactComponents) {
            components.add(component);
        }
    }

    /** Go through all of the direct child components of the current wireframe, convert all of the recognized components to their corresponding React Native component.
     * All of unrecognized components would be converted to an image.
     * @param wireframe
     * @throws IOException
     */
    public ReactWireframe(FigmaWireframe wireframe) {
        this.wireframe = wireframe;
        this.backgroundColor = wireframe.getBackgroundColor();
        this.absoluteBoundingBox = wireframe.getAbsoluteBoundingBox();
        for (FigmaComponent component : wireframe.getComponents()) {
            ReactComponent reactComponent = component.convertToFrontendComponent();
            components.add(reactComponent);
        }
    }


    /**
     *  Function used to combine import code with the view code
     *  to generate the content of the whole file.
     */
    public String generateCode() throws IOException {
        StringBuilder code = new StringBuilder();
        code.append(generateImportCode());
        code.append(generateViewCode());
        return code.toString();
    }

    public <T extends ReactComponent> boolean containsComponent(Class<T> cls) {
        return ReactComponent.containsComponent(components, cls);
    }

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
        importCode.append("import base from '../../assets/BaseStyle.js';\n");

        if (containsComponent(TextComponent.class) || containsComponent(ChartComponent.class)) {
            importCode.append("import P from '../reusables/P.js';\n");
        }
        if (containsComponent(ButtonComponent.class)) {
            importCode.append("import Button from '../reusables/Button.js';\n");
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
    public String generateViewCode() {
        StringBuilder viewCode = new StringBuilder();
        viewCode.append("class ").append(wireframe.getName()).append(" extends Component {\n");
        viewCode.append("render() {\n");
        viewCode.append("        return (\n" +
                "            <ScrollView style={{flex: 1, padding: 0, backgroundColor: ").append(backgroundColor.toString()).append("}}>\n");
        if (components.isEmpty()) { return ""; }
        // Put all of the components in the same line in one list
        List<List<ReactComponent>> inlineComponentList = ReactComponent.getInlineComponentList(components);
        int preY = absoluteBoundingBox.getY();
        for (List<ReactComponent> line : inlineComponentList) {
            // There is only one component in this line. Equations for positioning:
            // widthWireframe = marginLeft + widthElement + marginRight
            // marginLeft = positionXElement - positionXWireframe
            // marginTop = positionYElement - positionYPreviousElement - heightPreviousElement
            if (line.size() == 1) {
                ReactComponent component = line.get(0);
                int marginTop = Math.max(component.getPositionY() - preY, 0);
                int marginLeft = component.getPositionX() - absoluteBoundingBox.getX();
                viewCode.append(String.format("<View style={{marginTop: %d, marginLeft: %d}}>\n", marginTop, marginLeft));
                viewCode.append(component.generateCode()).append("\n</View>\n");
                preY = component.getPositionY() + component.getHeight();
            }

            // If there are multiple components in this line, then align these content using 'space-between'
            else if (line.size() > 1) {
                int minY = Integer.MAX_VALUE;
                int maxY = -1;
                for (ReactComponent component : line) {
                    if (component.getPositionY() < minY) {
                        minY = component.getPositionY();
                    }
                }
                int marginTop = Math.max(minY - preY, 0);
                viewCode.append(String.format("<View style={{flexDirection: 'row', justifyContent: 'space-between', marginTop: %d}}>\n", marginTop));
                for (ReactComponent component : line) {
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
                "export default ").append(wireframe.getName());

        return viewCode.toString();
    }

}
