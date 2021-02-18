package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponents.FrontendComponent;
import com.ucl.imaginethisserver.FrontendComponents.TextComponent;

import java.util.List;
/**
 *  A object used to store data about this specific component passed from Figma API,
 *  basically represents a text.
 *  The object is used to store all usable styles:
 *  Fills(color or background), fonts, contents of the text etc.
 *  Need to be converted to frontend component before used to convert into the real code.
 */
public class Text extends FigmaComponent {
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
    public TextComponent convertToFrontendText(){
        TextComponent textComponent = new TextComponent();
        textComponent.setWidth(this.getWidth());
        textComponent.setHeight(this.getHeight());
        textComponent.setPositionX(this.getPositionX());
        textComponent.setPositionY(this.getPositionY());
        textComponent.setAlign(this.getAlign());
        textComponent.setColor(this.getFills().get(0).getColor());
        textComponent.setText(this.characters);
        textComponent.setFontFamily(this.style.getFontFamily());
        textComponent.setFontSize(this.style.getFontSize());
        textComponent.setFontWeight(this.style.getFontWeight());
        textComponent.setTextAlign(this.style.getTextAlignHorizontal());
        return textComponent;
    }

    public FrontendComponent convertToFrontendComponent() {
        return null;
    };


}
