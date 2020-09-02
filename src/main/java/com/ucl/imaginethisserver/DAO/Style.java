package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;
/**
 *  The class that represents the Style of the Text component
 *  including font style, text align etc.
 */
public class Style {
    @Expose()
    private String fontFamily;
    @Expose()
    private String fontPostScriptName;
    @Expose()
    private String fontWeight;
    @Expose()
    private String fontSize;
    @Expose()
    private String textAlignHorizontal;
    @Expose()
    private String textAlignVertical;
    @Expose()
    private double letterSpacing;
    @Expose()
    private double lineHeightPx;
    @Expose()
    private double lineHeightPercent;
    @Expose()
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
