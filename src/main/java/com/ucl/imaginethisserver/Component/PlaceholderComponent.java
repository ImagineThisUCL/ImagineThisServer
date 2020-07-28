package com.ucl.imaginethisserver.Component;

public class PlaceholderComponent {
    public static String generateCode(){
        return "import { View, StyleSheet, Text } from \"react-native\"\n" +
                "import React, { Component } from \"react\"\n" +
                "import { StatusBar } from 'expo-status-bar'\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    container: {\n" +
                "        flex: 1,\n" +
                "        backgroundColor: '#fff',\n" +
                "        alignItems: 'center',\n" +
                "        justifyContent: 'center',\n" +
                "    },\n" +
                "    text: {\n" +
                "        marginBottom: 20,\n" +
                "        fontSize: 20,\n" +
                "        textAlign: \"center\"\n" +
                "    }\n" +
                "})\n" +
                "\n" +
                "class Placeholder extends Component {\n" +
                "    render() {\n" +
                "        return (\n" +
                "            <View style={styles.container}>\n" +
                "                <StatusBar style=\"auto\" />\n" +
                "                <Text style={styles.text}>\n" +
                "                    Placeholder view\n" +
                "                </Text>\n" +
                "            </View>\n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "export default Placeholder";
    }
}
