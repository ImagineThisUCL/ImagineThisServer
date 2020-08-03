package com.ucl.imaginethisserver.Component;

public class emptyComponent {
    public static String generateCode(){
        return "import React, { Component } from 'react';\n" +
                "import { Text, View } from 'react-native';\n" +
                "\n" +
                "class Empty extends Component {\n" +
                "    render() {\n" +
                "        return (\n" +
                "            <View>\n" +
                "                <Text>The redirected page has not been converted yet</Text>\n" +
                "                <Text>This might be because you haven't select it on the code convert page</Text>\n" +
                "                <Text>or because this page does not exist.</Text>\n" +
                "            </View>\n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "export default Empty";
    }
}
