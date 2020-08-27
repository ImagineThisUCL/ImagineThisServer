package com.ucl.imaginethisserver.Component;

public class ImageButtonComponent {
    public static String generateCode(){
        return "import React, {\n" +
                "    Component\n" +
                "} from \"react\"\n" +
                "import {\n" +
                "    StyleSheet,\n" +
                "    Image,\n" +
                "    TouchableOpacity,\n" +
                "    View,\n" +
                "} from \"react-native\"\n" +
                "import propTypes from 'prop-types'\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    wrapper: {\n" +
                "        flexDirection: \"row\",\n" +
                "    },\n" +
                "})\n" +
                "\n" +
                "export default class Button extends Component {\n" +
                "    render() {\n" +
                "        return (\n" +
                "            <View style={styles.wrapper}>\n" +
                "                <TouchableOpacity \n" +
                "                    {...this.props} \n" +
                "                    style={this.props.style}>\n" +
                "                    <Image\n" +
                "                        style={this.props.imageStyle}\n" +
                "                        source={this.props.imageSrc}>\n" +
                "                    </Image>\n" +
                "                </TouchableOpacity>\n" +
                "            </View>\n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "Button.propTypes = {\n" +
                "    imageSrc: propTypes.node..isRequired,\n" +
                "    style: propTypes.object, // container styles\n" +
                "    imageStyle: propTypes.object, // image styles\n" +
                "}\n";
    }
}
