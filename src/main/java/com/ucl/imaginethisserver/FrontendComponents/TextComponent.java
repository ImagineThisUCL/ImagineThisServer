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
    private String text;
    private String textAlign;
    private String fontSize;
    private String fontWeight;
    private String fontFamily;
    private Color color;

    @Override
    public boolean requiresReusableComponent() { return true; };

    @Override
    public String getReusableComponentName() { return "P.js"; };

    @Override
    public String generateCode(){
        try {
            String colorString = color.toString();
            return "<P style={{fontSize: " + fontSize + ", fontWeight: \'" + fontWeight + "\', color: " + color + ", textAlign: \'" + textAlign.toLowerCase() + "\', flex: 1 }}>" + text + "</P>";
        } catch (Exception e) {
            return "<P>The text code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public Color getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public String getTextAlign() { return textAlign; }


    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }


    public String toString(){
        return this.getHeight() + " " + this.getPositionY();
    }

}
