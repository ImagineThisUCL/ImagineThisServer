package com.ucl.imaginethisserver.Component;

public class SwitchComponent {
    public static String generateCode(){
        return "import React, { Component } from \"react\"\n" +
                "import propTypes from 'prop-types'\n" +
                "import {Switch } from \"react-native\";\n" +
                "import { color } from \"react-native-reanimated\";\n" +
                "\n" +
                "export default class Toggle extends Component {\n" +
                "    constructor(props) {\n" +
                "        super(props);\n" +
                "        this.state = {\n" +
                "          toggle: false,\n" +
                "        };\n" +
                "    }\n" +
                "\n" +
                "    render() {\n" +
                "\n" +
                "        return (\n" +
                "            <Switch\n" +
                "                {...this.props} \n" +
                "                onValueChange={(value) => this.setState({toggle: value})}\n" +
                "                value={this.state.toggle}\n" +
                "            />  \n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "Toggle.propTypes = {\n" +
                "    // prop types for react-native Switch\n" +
                "    disabled: propTypes.bool,\n" +
                "    ios_backgroundColor: propTypes.string,\n" +
                "    onChange: propTypes.func,\n" +
                "    onValueChange: propTypes.func,\n" +
                "    thumbColor: propTypes.string,\n" +
                "    trackColor: propTypes.object,\n" +
                "    value: propTypes.bool,\n" +
                "}\n";
    }
}
