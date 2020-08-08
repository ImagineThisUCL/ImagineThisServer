package com.ucl.imaginethisserver.FrontendComponent;

public class Dropdown extends FrontendComponent {
    public String generateCode(){
        StringBuilder code = new StringBuilder();
        code.append("<Dropdown\n" +
                "items={[\n" +
                "{key: 'option1', label: 'Placeholder'},\n" +
                "{key: 'option2', label: 'Placeholder'},\n" +
                "{key: 'option3', label: 'Placeholder'},\n" +
                "]}\n" +
                "containerStyle={{borderRadius: 10, backgroundColor: \"#fafafa\" }}\n" +
                "textStyle={{fontSize: 20, color: \"#0d0d0d\"}}/>\n");
        return code.toString();
    }
}
