package com.ucl.imaginethisserver.Component;

public class ButtonComponent {
    public static String generateCode(){
        return "import React, {\n" +
                "    Component\n" +
                "} from \"react\"\n" +
                "import {\n" +
                "    StyleSheet,\n" +
                "    Text,\n" +
                "    TouchableOpacity,\n" +
                "    View,\n" +
                "} from \"react-native\"\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    button: {\n" +
                "        marginBottom: base.margin,\n" +
                "        paddingHorizontal: base.padding*2,\n" +
                "        paddingVertical: base.padding,\n" +
                "    },\n" +
                "    text: {\n" +
                "        fontSize: base.font.medium,\n" +
                "    },\n" +
                "    wrapper: {\n" +
                "        flexDirection: \"row\",\n" +
                "    },\n" +
                "})\n" +
                "\n" +
                "export default class Button extends Component {\n" +
                "    render() {\n" +
                "        let buttonStyleList = [styles.button]\n" +
                "        let textStyleList = [styles.text]\n" +
                "\n" +
                "        return (\n" +
                "            <View style={styles.wrapper}>\n" +
                "                <TouchableOpacity \n" +
                "                    {...this.props} \n" +
                "                    style={[...buttonStyleList, this.props.style]}>\n" +
                "                    <Text\n" +
                "                        style={[...textStyleList, this.props.textStyle]}>\n" +
                "                        {this.props.children}\n" +
                "                    </Text>\n" +
                "                </TouchableOpacity>\n" +
                "            </View>\n" +
                "        )\n" +
                "    }\n" +
                "}";
    }
}
