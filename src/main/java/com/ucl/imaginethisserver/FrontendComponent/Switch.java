package com.ucl.imaginethisserver.FrontendComponent;

public class Switch extends FrontendComponent {
    public String generateCode() {
        StringBuilder code = new StringBuilder();
        code.append("<Toggle\n" +
                "trackColor={{ false: \"#767577\", true: \"#81b0ff\" }}\n" +
                "thumbColor=\"white\"\n" +
                "ios_backgroundColor=\"#3e3e3e\"\n" +
                "//    onValueChange={toggleSwitch}\n" +
                "//    value={isEnabled}\n" +
                "/>");
        return code.toString();
    }
}
