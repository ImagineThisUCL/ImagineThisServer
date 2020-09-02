package com.ucl.imaginethisserver.Component;

public class BaseStyleComponent {
    /**
     *  Function contains fixed code content of baseStyle.js
    */
    public static String generateCode(){
        return "import { Dimensions } from \"react-native\"\n" +
                "\n" +
                "const width = Dimensions.get(\"window\").width\n" +
                "const height = Dimensions.get(\"window\").height\n" +
                "\n" +
                "const baseSpacing = 10\n" +
                "\n" +
                "export default {\n" +
                "  margin: baseSpacing,\n" +
                "  padding: baseSpacing,\n" +
                "  paddingExtra: baseSpacing * 4,\n" +
                "  screenPadding: baseSpacing,\n" +
                "  borderRadius: baseSpacing,\n" +
                "  colors: {\n" +
                "    bluePrimary: \"#245271\",\n" +
                "    greenPrimary: \"#6dbe29\",\n" +
                "    blueSecondary: \"#4c718b\",\n" +
                "    blueHeadings: \"#3f7489\",\n" +
                "    greenLight: \"#a2d577\",\n" +
                "    greenDark: \"#579821\",\n" +
                "    blueLight: \"#8fb9d5\",\n" +
                "    darkPrimary: \"#333333\",\n" +
                "    darkSecondary: \"#666666\",\n" +
                "    grey: \"#999999\",\n" +
                "    greyLight: \"#cccccc\",\n" +
                "    greyLightest: \"#eeeeee\",\n" +
                "    red: \"#f11c30\",\n" +
                "    white: \"#ffffff\",\n" +
                "    modalBackground: \"#00000080\"\n" +
                "\n" +
                "  },\n" +
                "  window: {\n" +
                "    width,\n" +
                "    height\n" +
                "  },\n" +
                "  layout: {\n" +
                "    isSmallDevice: width < 375\n" +
                "  },\n" +
                "  font: {\n" +
                "    smallest: 14,\n" +
                "    small: 17,\n" +
                "    medium: 19,\n" +
                "    large: 21,\n" +
                "    heading: 25,\n" +
                "    iconSmallest: 19,\n" +
                "    iconSmall: 22,\n" +
                "    iconMedium: 24,\n" +
                "    iconLarge: 27\n" +
                "  }\n" +
                "}\n";
    }
}
