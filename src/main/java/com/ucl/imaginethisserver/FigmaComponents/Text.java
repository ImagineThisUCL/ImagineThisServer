package com.ucl.imaginethisserver.FigmaComponents;

import com.google.gson.annotations.Expose;
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
    private Style style;
    @Expose()
    private List<Effect> effects;
    @Expose()
    private String characters;

    public Style getStyle() {
        return style;
    }

    public String getCharacters() {
        return characters;
    }

    /**
     *  Function used to convert Text into a FrontendText,
     *  so that after being converted, these information can be used to
     *  generate actual frontend code.
    */
    @Override
    public TextComponent convertToFrontendComponent(){
        TextComponent textComponent = new TextComponent();
        textComponent.setWidth(getWidth());
        textComponent.setHeight(getHeight());
        textComponent.setPositionX(getPositionX());
        textComponent.setPositionY(getPositionY());
        textComponent.setAlign(getAlign());
        textComponent.setText(getCharacters());
        textComponent.setTextFills(getFills());
        textComponent.setStyle(getStyle());

        return textComponent;
    }

}
