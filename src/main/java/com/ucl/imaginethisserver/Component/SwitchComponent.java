package com.ucl.imaginethisserver.Component;

public class SwitchComponent {
    public static String generateCode(){
        return "import React, { Component } from \"react\"\n" +
                "import { StyleSheet } from \"react-native\"\n" +
                "import { Button } from 'react-native-elements';\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "import { Switch } from \"react-native-gesture-handler\";\n" +
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
                "}\n";
    }
}
