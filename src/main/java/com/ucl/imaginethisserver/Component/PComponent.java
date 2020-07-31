package com.ucl.imaginethisserver.Component;

import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;

public class PComponent {
    public static String generateCode(){
        return "import React, { Component } from \"react\"\n" +
                "import { StyleSheet } from \"react-native\"\n" +
                "import {Text} from \"react-native\"\n" +
                "\n" +
                "import base from \"../../assets/baseStyle\"\n" +
                "\n" +
                "const styles = StyleSheet.create({\n" +
                "  default: {\n" +
                "    marginBottom: base.margin,\n" +
                "    fontSize: base.font.smallest\n" +
                "  },\n" +
                "  noMargin: {\n" +
                "    marginBottom: 0\n" +
                "  },\n" +
                "  smallMargin: {\n" +
                "    marginBottom: base.margin / 2\n" +
                "  },\n" +
                "  topMargin: {\n" +
                "    marginTop: base.margin\n" +
                "  },\n" +
                "  smallFont: {\n" +
                "    fontSize: base.font.small\n" +
                "  },\n" +
                "  mediumFont: {\n" +
                "    fontSize: base.font.medium\n" +
                "  },\n" +
                "  largeFont: {\n" +
                "    fontSize: base.font.large\n" +
                "  },\n" +
                "  heading: {\n" +
                "    fontSize: base.font.large,\n" +
                "    color: base.colors.blueHeadings\n" +
                "  },\n" +
                "  smallHeading: {\n" +
                "    fontSize: base.font.small,\n" +
                "    color: base.colors.blueHeadings,\n" +
                "    marginBottom: base.margin / 3\n" +
                "  },\n" +
                "  largeHeading: {\n" +
                "    fontSize: base.font.heading,\n" +
                "    color: base.colors.white,\n" +
                "    paddingHorizontal: base.padding,\n" +
                "    marginBottom: base.margin * 1.5\n" +
                "  },\n" +
                "  textCenter: {\n" +
                "    textAlign: \"center\"\n" +
                "  },\n" +
                "  secondary: {\n" +
                "    color: base.colors.blueSecondary\n" +
                "  },\n" +
                "  grey: {\n" +
                "    color: base.colors.grey\n" +
                "  },\n" +
                "  dark: {\n" +
                "    color: base.colors.darkSecondary\n" +
                "  },\n" +
                "  bottomSeparator: {\n" +
                "    borderBottomWidth: 1,\n" +
                "    borderBottomColor: base.colors.greyLight,\n" +
                "    paddingBottom: base.padding / 2\n" +
                "  },\n" +
                "\n" +
                "\n" +
                "})\n" +
                "\n" +
                "class P extends Component {\n" +
                "  render() {\n" +
                "    let styleList = [styles.default]\n" +
                "    if (this.props.noMargin) styleList.push(styles.noMargin)\n" +
                "    if (this.props.topMargin) styleList.push(styles.topMargin)\n" +
                "    if (this.props.smallMargin) styleList.push(styles.smallMargin)\n" +
                "    if (this.props.smallFont) styleList.push(styles.smallFont)\n" +
                "    if (this.props.mediumFont) styleList.push(styles.mediumFont)\n" +
                "    if (this.props.largeFont) styleList.push(styles.largeFont)\n" +
                "    if (this.props.heading) styleList.push(styles.heading)\n" +
                "    if (this.props.smallHeading) styleList.push(styles.smallHeading)\n" +
                "    if (this.props.largeHeading) styleList.push(styles.largeHeading)\n" +
                "    if (this.props.textCenter) styleList.push(styles.textCenter)\n" +
                "    if (this.props.secondary) styleList.push(styles.secondary)\n" +
                "    if (this.props.red) styleList.push(styles.red)\n" +
                "    if (this.props.grey) styleList.push(styles.grey)\n" +
                "    if (this.props.dark) styleList.push(styles.dark)\n" +
                "    if (this.props.bottomSeparator) styleList.push(styles.bottomSeparator)\n" +
                "    styleList.push({fontSize: this.props.fontSize})\n" +
                "\n" +
                "    return (\n" +
                "      <Text {...this.props} style={[...styleList, this.props.style]}/>\n" +
                "    )\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "export default P;";
    }
}
