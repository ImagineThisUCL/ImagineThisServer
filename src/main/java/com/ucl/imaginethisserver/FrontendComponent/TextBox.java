package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.Fills;
import com.ucl.imaginethisserver.DAO.Style;

import java.util.List;

public class TextBox extends FrontendComponent{
    private String placeholder;
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
        StringBuilder textBoxCode = new StringBuilder();
        textBoxCode.append("<InputField\n");
        if(this.placeholder!=null){
            textBoxCode.append("placeholder='"+ this.placeholder +"'\n");
        }
        textBoxCode.append("inputContainerStyle={{");
        if(this.containerFills!=null){
            String containerColor = this.containerFills.get(0).getColor().toString();
            textBoxCode.append("backgroundColor: "+ containerColor+",");
        }
        textBoxCode.append("borderRadius: " + this.getCornerRadius()+"}}\n");
        textBoxCode.append("/>");
        return textBoxCode.toString();
    }

}
