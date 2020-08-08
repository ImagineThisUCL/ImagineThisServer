package com.ucl.imaginethisserver.Component;

public class DropdownComponent {
    public static String generateCode(){
        return "import React, { Component } from \"react\"\n" +
                "import { StyleSheet } from \"react-native\"\n" +
                "import ModalSelector from 'react-native-modal-selector'\n" +
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
                "})\n" +
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
                "                initValue=\"Please select\"\n" +
                "                selectStyle={containerStyle}\n" +
                "                selectTextStyle={textStyle}\n" +
                "                initValueTextStyle={[textStyle, styles.initText]}\n" +
                "                optionTextStyle={styles.modalText}\n" +
                "                optionContainerStyle={styles.modalContainer}\n" +
                "                cancelTextStyle={styles.cancelText}\n" +
                "                cancelStyle={styles.modalContainer}\n" +
                "                cancelText=\"Cancel\" />\n" +
                "        )\n" +
                "    }\n" +
                "}\n";
    }
}
