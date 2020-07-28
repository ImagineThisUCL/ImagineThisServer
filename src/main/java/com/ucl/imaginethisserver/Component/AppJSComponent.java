package com.ucl.imaginethisserver.Component;

import com.ucl.imaginethisserver.FrontendComponent.NavBar;

import java.util.HashSet;

public class AppJSComponent {

    public static String generateImportCode(NavBar navBar){
        StringBuilder importCode = new StringBuilder();
        importCode.append("import React from 'react'\n" +
                "import { NavigationContainer } from '@react-navigation/native'\n" +
                "import { createStackNavigator } from '@react-navigation/stack'").append('\n');
        importCode.append("import { createBottomTabNavigator } from '@react-navigation/bottom-tabs'\n");
        importCode.append("import { StyleSheet, Text, SafeAreaView, StatusBar} from 'react-native';\n");
        importCode.append("import Image from \"react-native-web/src/exports/Image\";\n");
        HashSet<String> wireframeNameSet = new HashSet<>(NavBar.BUTTON_MAP.values());
        for(String wireframeName : wireframeNameSet){
            wireframeName = wireframeName.replaceAll(" ","");
            importCode.append("import ").append(wireframeName).append(" from \"./components/views/").append(wireframeName).append("\"\n");
        }
        importCode.append("\n");
        return importCode.toString();
    }

    public static String generateViewCode(NavBar navBar){
        StringBuilder viewCode = new StringBuilder();
        viewCode.append("const Tab = createBottomTabNavigator();").append("\n");
        viewCode.append(navBar.generateCode());
        viewCode.append("const Stack = createStackNavigator();").append("\n");
        viewCode.append("export default function App() {\n" +
                "    return (\n" +
                "        <>\n" +
                "        <SafeAreaView style={{flex: 0, backgroundColor: '#ffffff'}}/>\n" +
                "        <SafeAreaView style={{flex: 1, backgroundColor: \"#D5E6EC\"}}>\n" +
                "\n" +
                "            <NavigationContainer>\n" +
                "                <Stack.Navigator initialRouteName=\"Home\">\n" +
                "                    <Stack.Screen\n" +
                "                        name=\"Home\"\n" +
                "                        component={NavigationBar}\n" +
                "                        options={{headerShown: false}}/>\n" +
                "                </Stack.Navigator>\n" +
                "            </NavigationContainer>\n" +
                "\n" +
                "        </SafeAreaView>\n" +
                "        </>\n" +
                "    )\n" +
                "}");
        return viewCode.toString();

    }

    public static String generateCode(NavBar navBar){
        return generateImportCode(navBar) +
                generateViewCode(navBar);
    }
}
