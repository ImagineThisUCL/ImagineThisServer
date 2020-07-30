package com.ucl.imaginethisserver.Component;

public class InputFieldComponent {
    public static String generateCode(){
        return "import React, { Component } from \"react\"\n" +
                "import { StyleSheet } from \"react-native\"\n" +
                "import { Input } from \"react-native-elements\";\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    input: {\n" +
                "        flexDirection: \"row\"\n" +
                "    },\n" +
                "})\n" +
                "\n" +
                "export default class InputField extends Component {\n" +
                "    render() {\n" +
                "\n" +
                "        let inputStyleList = [styles.input]\n" +
                "\n" +
                "        return (\n" +
                "            <Input\n" +
                "                style={[...inputStyleList, this.props.style]}\n" +
                "                {...this.props}\n" +
                "            />\n" +
                "        )\n" +
                "    }\n" +
                "}\n";
    }
}
