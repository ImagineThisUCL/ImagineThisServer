package com.ucl.imaginethisserver.Component;

public class InputFieldComponent {
    public static String generateCode(){
        return "import React, { Component } from \"react\"\n" +
                "import { StyleSheet } from \"react-native\"\n" +
                "import { Input } from \"react-native-elements\";\n" +
                "import propTypes from 'prop-types'\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    input: {\n" +
                "        flexDirection: \"row\",\n" +
                "        minHeight: 40,\n" +
                "    },\n" +
                "})\n" +
                "\n" +
                "/*\n" +
                " * A highly customisable Input Field component\n" +
                "*/\n" +
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
                "}\n" +
                "\n" +
                "InputField.propTypes = {\n" +
                "    // props for react-native TextInput \n" +
                "    placeholder: propTypes.string,\n" +
                "\n" +
                "    // props for react-native-elements Input\n" +
                "    containerStyle: propTypes.object,\n" +
                "    disabled: propTypes.bool,\n" +
                "    disabledInputStyle: propTypes.object,\n" +
                "    inputContainerStyle: propTypes.object,\n" +
                "    errorMessage: propTypes.string,\n" +
                "    errorStyle: propTypes.object,\n" +
                "    inputStyle: propTypes.object,\n" +
                "    label: propTypes.string,\n" +
                "    labelStyle: propTypes.object,\n" +
                "    leftIcon: propTypes.object,\n" +
                "    leftIconContainerStyle: propTypes.object,\n" +
                "    rightIcon: propTypes.object,\n" +
                "    rightIconContainerStyle: propTypes.object,\n" +
                "}\n";
    }
}
