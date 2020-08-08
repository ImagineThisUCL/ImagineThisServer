package com.ucl.imaginethisserver.Component;

public class FixedSwitchComponent {
    public static String generateCode(){
        return "const [isEnabled, setIsEnabled] = useState(false);\n" +
               "const toggleSwitch = () => setIsEnabled(previousState => !previousState);\n";
    }
}
