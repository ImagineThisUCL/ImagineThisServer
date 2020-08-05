package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.FigmaColor;
import com.ucl.imaginethisserver.DAO.Fills;
import com.ucl.imaginethisserver.DAO.Page;
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
        StringBuilder buttonCode = new StringBuilder();
        buttonCode.append("<Button\n");
        if(this.transitionNodeID!=null){
            String navigateWireframe = Page.getWireframeByID(transitionNodeID).getName().replaceAll(" ","");
            buttonCode.append("onPress={() => this.props.navigation.navigate('").append(navigateWireframe).append("')}\n");
        }
        buttonCode.append("   style={{");
        if(this.RecFills != null){
            String backgroundColor = this.RecFills.get(0).getColor().toString();
            buttonCode.append("backgroundColor:").append(backgroundColor).append(", ");
        }
        buttonCode.append("borderRadius: ").append(this.getCornerRadius()).append(", marginTop: base.margin");
        if(this.borderColor!=null){
            String borderColorStr = this.borderColor.toString();
            buttonCode.append(", borderColor: ").append(borderColorStr).append(" ,borderWidth: ").append(this.borderWidth);
        }
        buttonCode.append("}}\n");
        buttonCode.append("   textStyle={{");
        if(this.TextFills != null){
            String textColor = this.TextFills.get(0).getColor().toString();
            buttonCode.append("color: ").append(textColor);
        }
        if(this.style != null){
            buttonCode.append(", fontSize: ").append(this.style.getFontSize());
        }
        buttonCode.append("}}>\n").append(this.character).append("</Button>");
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

