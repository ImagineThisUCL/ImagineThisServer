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
        String fontSize = getStyle().getFontSize();
        String fontWeight = getStyle().getFontWeight();
        String textAlign = getStyle().getTextAlignHorizontal();

        try {
            String colorString = getTextFills().get(0).getColor().toString();
            return "<P style={{fontSize: " + fontSize + ", fontWeight: \'" + fontWeight + "\', color: " + colorString + ", textAlign: \'" + textAlign.toLowerCase() + "\', flex: 1 }}>" + text + "</P>";
        } catch (Exception e) {
            return "<P>The text code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }

    }

    public String toString(){
        return this.getHeight() + " " + this.getPositionY();
    }

}
