package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.Fills;

import java.util.List;

public class Dropdown extends FrontendComponent {
    private String option;

    public void setOption(String string) {
        option = string;
    }

    public String generateCode(){
        StringBuilder code = new StringBuilder();
        code.append("<Dropdown\n" +
                "items={[\n" +
                "{key: 'option1', label: '");
        code.append(this.option);
        code.append("'},\n" +
                "{key: 'option2', label: 'Placeholder'},\n" +
                "]}\n" +
                "containerStyle={{borderRadius: 10, backgroundColor: \"#fafafa\" }}\n" +
                "textStyle={{fontSize: 20, color: \"#0d0d0d\"}}/>\n");
        return code.toString();
    }
}
