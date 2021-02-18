package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.FigmaComponents.Color;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *  As Figma Component cannot be used to generate code directly
 *  The figma component Text need to be converted into Frontend Component FrontendText
 *  in order to be used in code generation, inheriting most of the Text variables
 *  and modifying some to make it fits the frontend
*/
public class TextComponent extends FrontendComponent {
    private String fontSize;
    private String fontWeight;
    private String fontFamily;
    private Color color;
    private String text;
    private String textAlign;

    @Override
    public boolean isReusable() { return true; };

    @Override
    public String generateReusableCode() throws IOException {
        return readTemplateFile("P.js");
    };

    @Override
    public String generateCode(){
        try {
            this.text = this.text.replaceAll("\n", "{\"\\\\n\"}");
            String color = this.color.toString();
            return "<P style={{fontSize: " + this.getFontSize() + ", fontWeight: \'" + this.getFontWeight() + "\', color: " + color + ", textAlign: \'" + this.textAlign.toLowerCase() + "\', flex: 1 }}>" + this.text + "</P>";
        }catch (Exception e){
            return "<P>The text code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
