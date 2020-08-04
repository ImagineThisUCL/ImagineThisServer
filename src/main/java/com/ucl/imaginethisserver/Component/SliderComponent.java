package com.ucl.imaginethisserver.Component;

public class SliderComponent {
    public static String generateCode(){
        return "import React, {\n" +
                "    Component\n" +
                "} from \"react\"\n" +
                "import {\n" +
                "    StyleSheet,\n" +
                "    View,\n" +
                "    Text,\n" +
                "} from \"react-native\"\n" +
                "import Slider from '@react-native-community/slider'\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    labelsWrapper: {\n" +
                "        flexDirection: \"row\",\n" +
                "        justifyContent: \"space-between\",\n" +
                "        paddingHorizontal: 10,\n" +
                "        paddingVertical: 5,\n" +
                "    },\n" +
                "})\n" +
                "\n" +
                "/* This is an implementation using the @react-native-community/slider \n" +
                "which has web support in its @next version (^3.0.0-rc.2). The Slider \n" +
                "from react-native-elements is more customisable and preferred, \n" +
                "however does not run on the web.  */\n" +
                "\n" +
                "export default class CustomSlider extends Component {\n" +
                "    state = {\n" +
                "        sliderValue: this.props.value,\n" +
                "    }\n" +
                "\n" +
                "    render() {\n" +
                "        let labelStyleList = []\n" +
                "\n" +
                "        return (\n" +
                "            <View>\n" +
                "                <View style={styles.labelsWrapper}>\n" +
                "                    <Text style={[...labelStyleList, this.props.labelStyle]}>{this.props.minimumValue}</Text>\n" +
                "                    <Text style={[...labelStyleList, this.props.valueLabelStyle]}>{this.state.sliderValue}</Text>\n" +
                "                    <Text style={[...labelStyleList, this.props.labelStyle]}>{this.props.maximumValue}</Text>\n" +
                "                </View>\n" +
                "                <Slider {...this.props} \n" +
                "                    value={this.state.sliderValue}\n" +
                "                    onValueChange={(sliderValue) => this.setState({ sliderValue })}/>\n" +
                "            </View>\n" +
                "        )\n" +
                "    }\n" +
                "}";
    }
}
