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
                "import propTypes from 'prop-types'\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    button: {\n" +
                "        marginBottom: base.margin,\n" +
                "        paddingHorizontal: base.padding*2,\n" +
                "        paddingVertical: base.padding,\n" +
                "    },\n" +
                "    circleButton: {\n" +
                "        marginBottom: base.margin,\n" +
                "        padding: base.padding,\n" +
                "        alignItems: \"center\",\n" +
                "        justifyContent: \"center\",\n" +
                "    },\n" +
                "    text: {\n" +
                "        fontSize: base.font.medium,\n" +
                "        textAlign: \"center\",\n" +
                "    },\n" +
                "    wrapper: {\n" +
                "        flexDirection: \"row\",\n" +
                "    },\n" +
                "})\n" +
                "\n" +
                "export default class Button extends Component {\n" +
                "    render() {\n" +
                "        const {\n" +
                "            circleDiameter,\n" +
                "            style,\n" +
                "            textStyle,\n" +
                "            children,\n" +
                "        } = this.props\n" +
                "\n" +
                "        let buttonStyleList = []\n" +
                "        if (this.props.circleDiameter) {\n" +
                "            buttonStyleList = [styles.circleButton, {width: circleDiameter}, {height: circleDiameter}, {borderRadius: circleDiameter}, style]\n" +
                "        } else {\n" +
                "            buttonStyleList = [styles.button, style]\n" +
                "        }\n" +
                "\n" +
                "        let textStyleList = [styles.text, textStyle]\n" +
                "\n" +
                "        return (\n" +
                "            <View style={styles.wrapper}>\n" +
                "                <TouchableOpacity \n" +
                "                    {...this.props} \n" +
                "                    style={buttonStyleList}>\n" +
                "                    <Text\n" +
                "                        style={textStyleList}>\n" +
                "                        {children}\n" +
                "                    </Text>\n" +
                "                </TouchableOpacity>\n" +
                "            </View>\n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "Button.propTypes = {\n" +
                "    children: propTypes.node.isRequired, // button needs some content\n" +
                "    onPress: propTypes.func,\n" +
                "    style: propTypes.object, // container style\n" +
                "    textStyle: propTypes.object, // text style\n" +
                "    circleDiameter: propTypes.number, // create circular button with diameter\n" +
                "}\n";
    }
}
