package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.FigmaColor;

public class FrontendText extends FrontendComponent {
    private String fontSize;
    private String fontWeight;
    private String fontFamily;
    private FigmaColor color;
    private String text;
    private String textAlign;

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public FigmaColor getColor() {
        return color;
    }

    public void setColor(FigmaColor color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String generateCode(){
        try {
            this.text = this.text.replaceAll("\n", "{\"\\\\n\"}");
            String color = this.color.toString();
            return "<P style={{fontSize: " + this.getFontSize() + ", color: " + color + ", textAlign: \'" + this.textAlign.toLowerCase() + "\'}}>" + this.text + "</P>";
        }catch (Exception e){
            return "<P>The text code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }

    public String toString(){
        return this.getHeight() + " " + this.getPositionY();
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public void setFlex(int flex) {
        this.flex = flex;
    }
}
