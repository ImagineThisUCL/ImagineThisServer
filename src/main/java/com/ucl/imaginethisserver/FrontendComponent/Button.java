package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.DAO.FigmaColor;
import com.ucl.imaginethisserver.DAO.Fills;
import com.ucl.imaginethisserver.DAO.Style;
import com.ucl.imaginethisserver.DAO.Page;

import java.util.List;

public class Button extends FrontendComponent{
    private String character;
    private Style style;
    private List<Fills> TextFills;
    private List<Fills> RecFills;
    private String transitionNodeID;
    private double cornerRadius;
    private FigmaColor borderColor;
    private double borderWidth;

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }


    public String getTransitionNodeID() {
        return transitionNodeID;
    }

    public void setTransitionNodeID(String transitionNodeID) {
        this.transitionNodeID = transitionNodeID;
    }

    public double getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public String generateCode() {
        String backgroundColor = this.RecFills.get(0).getColor().toString();
        String textColor = this.TextFills.get(0).getColor().toString();

        StringBuilder buttonCode = new StringBuilder();
        buttonCode.append("<Button\n");

        System.out.println("ButtonTransitionID:"+this.transitionNodeID);
        if(this.transitionNodeID != null){
            buttonCode.append("   onPress={() => this.props.navigation.navigate('Empty')}\n");
        }

        buttonCode.append("   style={{backgroundColor:" + backgroundColor +
                ", borderRadius: " + this.getCornerRadius() +
                ", marginTop: base.margin");

        if(this.borderColor != null){
            String borderColorStr = this.borderColor.toString();
            buttonCode.append(", borderColor: " + borderColorStr +
                    " ,borderWidth: " + this.borderWidth);
        }

        buttonCode.append("}}\n" +
                "   textStyle={{color: " + textColor +
                ", fontSize: " + this.style.getFontSize() +
                "}}>\n" +
                this.character +
                "</Button>");
        return buttonCode.toString();
    }

    public void setTextFills(List<Fills> textFills) {
        TextFills = textFills;
    }

    public void setRecFills(List<Fills> recFills) {
        RecFills = recFills;
    }

    public String toString(){
        return this.height + " " + this.getPositionY();
    }

    public FigmaColor getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(FigmaColor borderColor) {
        this.borderColor = borderColor;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }
}

