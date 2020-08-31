package com.ucl.imaginethisserver.Component;

public class SliderComponent {
    /**
     *  Function contains fixed code content of CustomSlider.js
     *  Used to customize the slider on the frontend.
     */
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
                "import propTypes from 'prop-types'\n" +
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
                "    sliderWrapper: {\n" +
                "        flexDirection: \"column\",\n" +
                "        flexGrow: 1,\n" +
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
                "            <View style={styles.sliderWrapper}>\n" +
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
                "}\n" +
                "\n" +
                "CustomSlider.propTypes = {\n" +
                "    minimumValue: propTypes.number.isRequired,\n" +
                "    maximumValue: propTypes.number.isRequired,\n" +
                "    value: propTypes.number.isRequired,\n" +
                "    step: propTypes.number, // default is 1\n" +
                "    minimumTrackTintColor: propTypes.string, // track color for left side\n" +
                "    maximumTrackTintColor: propTypes.string, // track color for right side\n" +
                "    thumbTintColor: propTypes.string, // color of drag element\n" +
                "    labelStyle: propTypes.object, // style object for min/max labels\n" +
                "    valueLabelStyle: propTypes.object, // style object for value label\n" +
                "    /* \n" +
                "        For other props please refer to @react-native-community/slider documentation\n" +
                "    */\n" +
                "}\n";
    }
}
