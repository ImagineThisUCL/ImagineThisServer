package com.ucl.imaginethisserver.ReactComponents;


/**
 *  Switch object that only generates switch frontend code with fixed variables
 */
public class SwitchComponent extends ReactComponent {

    @Override
    public String getReusableComponentName() { return "Toggle.js"; }

    @Override
    public String generateCode() {
        try {
            StringBuilder code = new StringBuilder();
            code.append("<Toggle\n" +
                    "trackColor={{ false: \"#767577\", true: \"#81b0ff\" }}\n" +
                    "thumbColor=\"white\"\n" +
                    "ios_backgroundColor=\"#3e3e3e\"\n" +
                    "//    onValueChange={toggleSwitch}\n" +
                    "//    value={isEnabled}\n" +
                    "/>");
            return code.toString();
        } catch (Exception e) {
            return "<P>The switch component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }
}
