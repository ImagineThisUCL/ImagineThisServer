package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;

import java.util.List;

public class Text extends FigmaComponent implements FrontendComponent {
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
    @Expose()
    private String transitionNodeID;

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

    public String getTransitionNodeID() {
        return transitionNodeID;
    }

    public String generateCode(){
        return " <P fontSize ={" + this.style.getFontSize() +"}" + " textCenter = {true}>" + this.characters + "</P>";
    }
}
