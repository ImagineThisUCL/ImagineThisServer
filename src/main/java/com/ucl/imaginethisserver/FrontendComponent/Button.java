package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.FigmaColor;
import com.ucl.imaginethisserver.DAO.Fills;
import com.ucl.imaginethisserver.DAO.Style;

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
        if(this.borderColor!=null){
            String borderColorStr = this.borderColor.toString();
            return "<Button\n" +
                    "   style={{backgroundColor:" + backgroundColor +
                    ", borderRadius: " + this.getCornerRadius() +
                    ", marginTop: base.margin, borderColor: " + borderColorStr + " ,borderWidth: " + this.borderWidth +"}}\n" +
                    "   textStyle={{color: " + textColor +
                    ", fontSize: " + this.style.getFontSize() +
                    "}}>\n" +
                    this.character +
                    "</Button>";
        }else{
            return "<Button\n" +
                    "   style={{backgroundColor:" + backgroundColor +
                    ", borderRadius: " + this.getCornerRadius() +
                    ", marginTop: base.margin}}\n" +
                    "   textStyle={{color: " + textColor +
                    ", fontSize: " + this.style.getFontSize() +
                    "}}>\n" +
                    this.character +
                    "</Button>";
        }

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

