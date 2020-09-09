package com.ucl.imaginethisserver.Component;

public class DropdownComponent {
    public static String generateCode(){
        return "import React, { Component } from \"react\"\n" +
                "import { StyleSheet } from \"react-native\"\n" +
                "import ModalSelector from 'react-native-modal-selector'\n" +
                "import propTypes from 'prop-types'\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "    modalText: {\n" +
                "        fontSize: base.font.large,\n" +
                "        color: \"#000000\"\n" +
                "    },\n" +
                "    modalContainer: {\n" +
                "        backgroundColor: \"#f2f2f2\",\n" +
                "        borderRadius: base.borderRadius\n" +
                "    },\n" +
                "    cancelText: {\n" +
                "        fontSize: base.font.large,\n" +
                "        color: \"#5555ff\"\n" +
                "    },\n" +
                "    initText: {\n" +
                "        color: \"#8d8d8d\"\n" +
                "    },\n" +
                "    rootContainer: {\n" +
                "        flex: 1,\n" +
                "    },\n" +
                "})\n" +
                "\n" +
                "/*\n" +
                " * A custom implementation of a Dropdown that works together with \n" +
                " * a modal selector - to make appearance and behaviour consistent\n" +
                " * across iOS and Android.\n" +
                "*/\n" +
                "\n" +
                "export default class Dropdown extends Component {\n" +
                "    constructor(props) {\n" +
                "        super(props)\n" +
                "        this.state = { \n" +
                "            selected: this.props.selectedValue \n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    render() {\n" +
                "        const {\n" +
                "            items,\n" +
                "            textStyle,\n" +
                "            containerStyle,\n" +
                "        } = this.props\n" +
                "\n" +
                "        return (\n" +
                "            <ModalSelector\n" +
                "                data={items}\n" +
                "                selectedKey={items[0].key}\n" +
                "                selectStyle={containerStyle}\n" +
                "                style={styles.rootContainer}\n" +
                "                selectTextStyle={textStyle}\n" +
                "                initValueTextStyle={[textStyle, styles.initText]}\n" +
                "                optionTextStyle={styles.modalText}\n" +
                "                optionContainerStyle={styles.modalContainer}\n" +
                "                cancelTextStyle={styles.cancelText}\n" +
                "                cancelStyle={styles.modalContainer}\n" +
                "                cancelText=\"Cancel\" />\n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "Dropdown.propTypes = {\n" +
                "    items: propTypes.array.isRequired, // requires dropdown options\n" +
                "    textStyle: propTypes.object, // input box text style\n" +
                "    containerStyle: propTypes.object, // input box container style\n" +
                "    /* \n" +
                "        Other props are set directly in this file - for more information, please\n" +
                "        refer to react-native-modal-selector documentation\n" +
                "    */\n" +
                "}\n";
    }
}
