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
        //Delete defaultColor later
        String defaultColor = "\"#ffffff\"";
        String textColor = this.TextFills.get(0).getColor().toString();
        //String labelColor = this.labelFills.get(0).getColor().toString();

        String containerColor = this.containerFills.get(0).getColor().toString();

        return "<InputField\n" +
               "placeholder='"+ this.placeholder +"'\n" +
               "inputContainerStyle={{backgroundColor: "+ containerColor +
                ", borderRadius: " + this.getCornerRadius() +
               "}}\n" +
               "/>";
    }

}
