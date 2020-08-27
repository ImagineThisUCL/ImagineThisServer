package com.ucl.imaginethisserver.Component;

import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.FrontendComponent.Navigator;

import java.io.IOException;
import java.util.HashSet;

public class AppJSComponent {

    public static String generateImportCode(NavBar navBar) {
        StringBuilder importCode = new StringBuilder();
        importCode.append("import React from 'react'\n" +
                "import { NavigationContainer } from '@react-navigation/native'\n" +
                "import { createStackNavigator } from '@react-navigation/stack'").append('\n');
        if (navBar != null) {
            importCode.append("import { createBottomTabNavigator } from '@react-navigation/bottom-tabs'\n");
        }
        importCode.append("import { StyleSheet, Text, SafeAreaView, Image, StatusBar} from 'react-native';\n");
        HashSet<String> wireframeNameSet = new HashSet<>(NavBar.BUTTON_MAP.values());
        for (String wireframeName : wireframeNameSet) {
            wireframeName = wireframeName.replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ -]", "");
            importCode.append("import ").append(wireframeName).append(" from \"./components/views/").append(wireframeName).append("\"\n");
        }
        HashSet<String> importSet = new HashSet<>();
        for (String wireframeName : Navigator.NAVIGATOR_MAP.values()) {
            if (!wireframeNameSet.contains(wireframeName) && !importSet.contains(wireframeName)) {
                wireframeName = wireframeName.replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ -]", "");
                importCode.append("import ").append(wireframeName).append(" from \"./components/views/").append(wireframeName).append("\"\n");
                importSet.add(wireframeName);
            }
        }
        importCode.append("\n");
        return importCode.toString();
    }

    public static String generateViewCode(NavBar navBar) throws IOException {
        StringBuilder viewCode = new StringBuilder();
        String navBarName = "";
        if (navBar != null) {
            navBarName = "NavigationBar";
            viewCode.append("const Tab = createBottomTabNavigator();").append("\n");
            viewCode.append(navBar.generateCode());
        }
        viewCode.append("const Stack = createStackNavigator();").append("\n");
        viewCode.append("export default function App() {\n" +
                "    return (\n" +
                "        <>\n" +
                "        <SafeAreaView style={{flex: 0, backgroundColor: '#ffffff'}}/>\n" +
                "        <SafeAreaView style={{flex: 1, backgroundColor: \"#D5E6EC\"}}>\n" +
                "\n" +
                "            <NavigationContainer>\n" +
                "                <Stack.Navigator initialRouteName=\"" + navBarName + "\">\n");
        if (navBar != null && !navBar.isError) {
            viewCode.append("                    <Stack.Screen\n" +
                    "                        name=\"NavigationBar\"\n" +
                    "                        component={NavigationBar}\n" +
                    "                        options={{headerShown: false}}/>\n");
        }
        for (String wireframeKey : Navigator.NAVIGATOR_MAP.keySet()) {
            String wireframeComponent = Navigator.NAVIGATOR_MAP.get(wireframeKey).replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ -]", "");
            viewCode.append("                    <Stack.Screen\n" + "                        name=\"").append(wireframeKey.replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ -]", "")).append("\"\n").append("                        component={").append(wireframeComponent).append("}/>\n");
        }

        viewCode.append("                </Stack.Navigator>\n" +
                "            </NavigationContainer>\n" +
                "\n" +
                "        </SafeAreaView>\n" +
                "        </>\n" +
                "    )\n" +
                "}");
        return viewCode.toString();

    }

    public static String generateCode(NavBar navBar) throws IOException {
        return generateImportCode(navBar) +
                generateViewCode(navBar);
    }
}
