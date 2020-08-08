package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.Fills;
import com.ucl.imaginethisserver.DAO.Style;

import java.util.List;

public class Dropdown extends FrontendComponent {
    private String option;

    private double cornerRadius;
    private List<Fills> TextFills;
    private List<Fills> containerFills;
    private Style style;

    public void setOption(String string) {
        option = string;
    }

    public void setTextFills(List<Fills> textFills) {
        TextFills = textFills;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setContainerFills(List<Fills> conFills) {
        containerFills = conFills;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public String generateCode(){
        StringBuilder code = new StringBuilder();
        String textColor = this.TextFills.get(0).getColor().toString();
        String containerColor = this.containerFills.get(0).getColor().toString();

        code.append("<Dropdown\n" +
                "items={[\n" +
                "{key: 'option1', label: '");
        code.append(this.option);
        code.append("'},\n" +
                "{key: 'option2', label: 'Placeholder'},\n" +
                "]}\n");
        code.append("containerStyle={{borderRadius:").append(this.cornerRadius).append(", backgroundColor: ").append(containerColor).append("}}\n");
        code.append("textStyle={{fontSize: ").append(this.style.getFontSize()).append(", color: ").append(textColor).append("}}/>\n");
        return code.toString();
    }
}
