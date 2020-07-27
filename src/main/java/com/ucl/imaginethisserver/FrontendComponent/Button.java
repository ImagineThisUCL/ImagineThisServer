package com.ucl.imaginethisserver.FrontendComponent;

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
        return "<Button\n" +
                "   style={{backgroundColor:" + backgroundColor +
                ", borderRadius: " + this.getCornerRadius() +
                ", marginTop: base.margin, height: "+ (this.style.getFontSize()+25) +
                ", width: " + (this.style.getFontSize()+25) +
                "}}\n" +
                "   textStyle={{color: " + textColor +
                ", fontSize: " + this.style.getFontSize() +
                "}}>\n" +
                this.character +
                "</Button>";
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
}

