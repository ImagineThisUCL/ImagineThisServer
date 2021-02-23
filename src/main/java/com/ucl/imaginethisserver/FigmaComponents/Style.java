package com.ucl.imaginethisserver.FigmaComponents;


/**
 *  The class that represents the Style of the Text component
 *  including font style, text align etc.
 */
public class Style {
    private String fontFamily;
    private String fontPostScriptName;
    private String fontWeight;
    private String fontSize;
    private String textAlignHorizontal;
    private String textAlignVertical;
    private double letterSpacing;
    private double lineHeightPx;
    private double lineHeightPercent;
    private String lineHeightUnit;

    public String getFontFamily() {
        return fontFamily;
    }

    public String getFontPostScriptName() {
        return fontPostScriptName;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getTextAlignHorizontal() {
        return textAlignHorizontal;
    }

    public String getTextAlignVertical() {
        return textAlignVertical;
    }

    public double getLetterSpacing() {
        return letterSpacing;
    }

    public double getLineHeightPx() {
        return lineHeightPx;
    }

    public double getLineHeightPercent() {
        return lineHeightPercent;
    }

    public String getLineHeightUnit() {
        return lineHeightUnit;
    }
}
