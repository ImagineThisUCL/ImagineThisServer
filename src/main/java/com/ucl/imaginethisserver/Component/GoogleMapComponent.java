package com.ucl.imaginethisserver.Component;

public class GoogleMapComponent {
    /**
     *  Function contains fixed code content of GoogleMap.js
     */
    public static String generateCode(){
        return "import { View, StyleSheet} from \"react-native\"\n" +
                "import React, { Component } from \"react\"\n" +
                "import MapView ,{ PROVIDER_GOOGLE }from 'react-native-maps'\n" +
                "import propTypes from 'prop-types'\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const MAP_WIDTH = base.window.width\n" +
                "const MAP_HEIGHT = base.window.height / 1.4\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    container: {\n" +
                "        alignItems: 'center',\n" +
                "        justifyContent: 'center',\n" +
                "    },\n" +
                "    mapStyle: {\n" +
                "        width: MAP_WIDTH,\n" +
                "        height: MAP_HEIGHT,\n" +
                "    }\n" +
                "})\n" +
                "\n" +
                "/*\n" +
                " * A wrapper component for a Google Map\n" +
                "*/\n" +
                "\n" +
                "export default class GoogleMap extends Component {\n" +
                "    render() {\n" +
                "        return (\n" +
                "            <View style={styles.container}>\n" +
                "                <MapView \n" +
                "                    style={[styles.mapStyle, this.props.mapStyle]}\n" +
                "                    provider={PROVIDER_GOOGLE}\n" +
                "                    initialRegion={{\n" +
                "                        latitude: 51.50873,\n" +
                "                        longitude: -0.12574,\n" +
                "                        latitudeDelta: 0.0922,\n" +
                "                        longitudeDelta: 0.0421,\n" +
                "                    }}/>\n" +
                "            </View>\n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "GoogleMap.propTypes = {\n" +
                "    mapStyle: propTypes.object, // override default styles, e.g. set fixed width and height\n" +
                "}\n";
    }
}
