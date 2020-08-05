package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;
import com.ucl.imaginethisserver.FrontendComponent.FrontendText;

import java.util.List;

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
        return frontendText;
    }


}
