package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponent.FrontendText;

import java.util.List;
/**
 *  A object used to store data about this specific component passed from Figma API,
 *  basically represents a text.
 *  The object is used to store all usable styles:
 *  Fills(color or background), fonts, contents of the text etc.
 *  Need to be converted to frontend component before used to convert into the real code.
 */
public class Text extends FigmaComponent{
    @Expose()
    private String blendMode;
    @Expose()
    private List<Fills> fills;
    @Expose()
    private List<Stroke> strokes;
    @Expose()
    private Style style;
    @Expose()
    private List<Effect> effects;
    @Expose()
    private String characters;

    public String getBlendMode() {
        return blendMode;
    }

    public List<Fills> getFills() {
        return fills;
    }

    public List<Stroke> getStrokes() {
        return strokes;
    }

    public Style getStyle() {
        return style;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public String getCharacters() {
        return characters;
    }

    /**
     *  Function used to convert Text into a FrontendText,
     *  so that after being converted, these information can be used to
     *  generate actual frontend code.
    */
    public FrontendText convertToFrontendText(){
        FrontendText frontendText = new FrontendText();
        frontendText.setWidth(this.getWidth());
        frontendText.setHeight(this.getHeight());
        frontendText.setPositionX(this.getPositionX());
        frontendText.setPositionY(this.getPositionY());
        frontendText.setAlign(this.getAlign());
        frontendText.setColor(this.getFills().get(0).getColor());
        frontendText.setText(this.characters);
        frontendText.setFontFamily(this.style.getFontFamily());
        frontendText.setFontSize(this.style.getFontSize());
        frontendText.setFontWeight(this.style.getFontWeight());
        frontendText.setTextAlign(this.style.getTextAlignHorizontal());
        return frontendText;
    }


}
