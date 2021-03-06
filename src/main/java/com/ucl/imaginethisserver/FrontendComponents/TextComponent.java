package com.ucl.imaginethisserver.FrontendComponents;


/**
 *  As Figma Component cannot be used to generate code directly
 *  The figma component Text need to be converted into Frontend Component FrontendText
 *  in order to be used in code generation, inheriting most of the Text variables
 *  and modifying some to make it fits the frontend
*/
public class TextComponent extends FrontendComponent {

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getReusableComponentName() { return "P.js"; }

    @Override
    public String generateCode() {
        double fontSize = getStyle().getFontSize();
        int fontWeight = getStyle().getFontWeight();
        String textAlign = getStyle().getTextAlignHorizontal();
        String colorString = getTextFills().get(0).getColor().toString();
        try {
            return String.format("<P style={{fontSize: %f, fontWeight: '%d', color: %s, textAlign: '%s', flex: 1 }}>%s</P>", fontSize, fontWeight, colorString, textAlign.toLowerCase(), text);
        } catch (Exception e) {
            return "<P>The text code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }

    }

    public String toString(){
        return this.getHeight() + " " + this.getPositionY();
    }

}
