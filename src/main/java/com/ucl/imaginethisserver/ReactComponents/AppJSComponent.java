package com.ucl.imaginethisserver.ReactComponents;

import com.ucl.imaginethisserver.FigmaComponents.FigmaComponent;
import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import com.ucl.imaginethisserver.FigmaComponents.Navigation;
import com.ucl.imaginethisserver.FigmaComponents.Wireframe;

import java.io.IOException;

public class AppJSComponent {

    /**This method used to generate [import] section in the app.js
     * which components should be imported are determined by the components contained in the navigation bar and navigator.
     * @param figmaFile
     * @return
     */
    public static String generateImportCode(FigmaFile figmaFile) {
        StringBuilder importCode = new StringBuilder();
        importCode.append("import React from 'react';\n" +
                "import { NavigationContainer } from '@react-navigation/native';\n" +
                "import { createStackNavigator } from '@react-navigation/stack';\n" +
                "import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';\n" +
                "import { SafeAreaView, Image } from 'react-native';\n");
        for (Wireframe wireframe : figmaFile.getWireframes()) {
            String wireframeName = wireframe.getName();
            importCode.append("import " + wireframeName + " from './components/views/" + wireframeName + ".js';\n");
        }
        return importCode.toString();
    }

    /** Generate body source code for App.js
     * @param figmaFile
     * @return
     * @throws IOException
     */
    public static String generateViewCode(FigmaFile figmaFile) {
        StringBuilder viewCode = new StringBuilder();
        String initialRouteName = figmaFile.getInitialWireframeName();
        boolean containsNavigation = false;
        for (FigmaComponent component : figmaFile.getComponents()) {
            if (component instanceof Navigation) {
                containsNavigation = true;
                ReactComponent navBar = component.convertToFrontendComponent();
                viewCode.append("const Tab = createBottomTabNavigator();\n");
                viewCode.append(navBar.generateCode());
            }
        }
        viewCode.append("const Stack = createStackNavigator();\n");
        viewCode.append("export default function App() {\n" +
                "    return (\n" +
                "        <>\n" +
                "        <SafeAreaView style={{flex: 0, backgroundColor: '#ffffff'}}/>\n" +
                "        <SafeAreaView style={{flex: 1, backgroundColor: \"#D5E6EC\"}}>\n" +
                "\n" +
                "            <NavigationContainer>\n" +
                "                <Stack.Navigator initialRouteName='" + initialRouteName + "'>\n");
        if (containsNavigation) {
            viewCode.append("                    <Stack.Screen\n" +
                    "                        name='" + NavBarComponent.NAME + "'\n" +
                    "                        component={" + NavBarComponent.NAME + "}\n" +
                    "                        options={{headerShown: false}}/>\n");
        }
        // Add screens for every wireframe
        for (Wireframe wireframe : figmaFile.getWireframes()) {
            viewCode.append("<Stack.Screen\n" +
                            "name='" + wireframe.getName() + "'\n" +
                            "component={" + wireframe.getName() + "}/>\n");
        }

        viewCode.append("</Stack.Navigator>\n" +
                "</NavigationContainer>\n" +
                "\n" +
                "</SafeAreaView>\n" +
                "</>\n" +
                ")\n" +
                "}");
        return viewCode.toString();

    }

    /**
     *  Function that combine Import code and View code together.
     */
    public static String generateCode(FigmaFile figmaFile) {
        StringBuilder code = new StringBuilder();
        code.append(generateImportCode(figmaFile));
        code.append(generateViewCode(figmaFile));
        return code.toString();
    }
}
