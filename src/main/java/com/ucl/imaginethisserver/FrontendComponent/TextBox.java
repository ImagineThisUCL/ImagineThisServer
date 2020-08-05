package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.Fills;
import com.ucl.imaginethisserver.DAO.Style;

import java.util.List;

public class TextBox extends FrontendComponent{
    private String placeholder = null;
    private String label = null;
    private Style style;
    private List<Fills> containerFills;
    private List<Fills> labelFills;
    private List<Fills> TextFills;
    private double cornerRadius;

    public double getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setPlaceholder(String character) {
        this.placeholder = character;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setContainerFills(List<Fills> conFills) {
        containerFills = conFills;
    }

    public void setTextFills(List<Fills> textFills) {
        TextFills = textFills;
    }

    public String generateCode() {
        String containerColor = this.containerFills.get(0).getColor().toString();

        StringBuilder textBoxCode = new StringBuilder();
        textBoxCode.append("<InputField\n");
        if(label != null){
            textBoxCode.append("label='").append(this.label).append("'\n");
        }
        if(placeholder != null){
            textBoxCode.append("placeholder='").append(this.placeholder).append("'\n");
        }
        textBoxCode.append("inputContainerStyle={{backgroundColor: "+ containerColor +"\n" +
                "                , borderRadius: \"" + this.getCornerRadius() +"\"}}\n"+
                "               />");
        return textBoxCode.toString();
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
