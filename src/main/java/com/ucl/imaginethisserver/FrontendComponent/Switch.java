package com.ucl.imaginethisserver.FrontendComponent;

public class Switch extends FrontendComponent {
    public String generateCode() {
        StringBuilder code = new StringBuilder();
        code.append("<Switch\n" +
                "ios_backgroundColor=\"#3e3e3e\"\n" +
                "onValueChange={toggleSwitch}\n" +
                "value={isEnabled}\n" +
                "/>");
        return code.toString();
    }
}
