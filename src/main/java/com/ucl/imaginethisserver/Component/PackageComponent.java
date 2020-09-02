package com.ucl.imaginethisserver.Component;

public class PackageComponent {
    /**
     *  Function contains fixed code content of package.json
     */
    public static String generateCode(){
        return "{\n" +
                "  \"main\": \"node_modules/expo/AppEntry.js\",\n" +
                "  \"scripts\": {\n" +
                "    \"start\": \"expo start\",\n" +
                "    \"android\": \"expo start --android\",\n" +
                "    \"ios\": \"expo start --ios\",\n" +
                "    \"web\": \"expo start --web\",\n" +
                "    \"eject\": \"expo eject\"\n" +
                "  },\n" +
                "  \"dependencies\": {\n" +
                "    \"@react-native-community/masked-view\": \"0.1.10\",\n" +
                "    \"@react-native-community/picker\": \"1.6.0\",\n" +
                "    \"@react-native-community/slider\": \"3.0.0\",\n" +
                "    \"@react-navigation/bottom-tabs\": \"^5.7.2\",\n" +
                "    \"@react-navigation/native\": \"^5.7.1\",\n" +
                "    \"@react-navigation/stack\": \"^5.7.1\",\n" +
                "    \"expo\": \"^38.0.0\",\n" +
                "    \"expo-status-bar\": \"^1.0.0\",\n" +
                "    \"prop-types\": \"^15.7.2\",\n" +
                "    \"react\": \"16.11.0\",\n" +
                "    \"react-dom\": \"16.11.0\",\n" +
                "    \"react-native\": \"https://github.com/expo/react-native/archive/sdk-38.0.2.tar.gz\",\n" +
                "    \"react-native-chart-kit\": \"^6.4.1\",\n" +
                "    \"react-native-elements\": \"^2.0.4\",\n" +
                "    \"react-native-gesture-handler\": \"~1.6.0\",\n" +
                "    \"react-native-maps\": \"0.27.1\",\n" +
                "    \"react-native-modal-selector\": \"^2.0.2\",\n" +
                "    \"react-native-reanimated\": \"~1.9.0\",\n" +
                "    \"react-native-safe-area-context\": \"~3.0.7\",\n" +
                "    \"react-native-screens\": \"~2.9.0\",\n" +
                "    \"react-native-svg\": \"12.1.0\",\n" +
                "    \"react-native-web\": \"~0.11.7\"\n" +
                "  },\n" +
                "  \"devDependencies\": {\n" +
                "    \"@babel/core\": \"^7.8.6\",\n" +
                "    \"babel-preset-expo\": \"^8.2.3\"\n" +
                "  },\n" +
                "  \"private\": true\n" +
                "}\n";
    }
}
