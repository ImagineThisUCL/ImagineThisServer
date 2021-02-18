package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.FigmaComponents.Color;
import com.ucl.imaginethisserver.FigmaComponents.Fills;
import com.ucl.imaginethisserver.FigmaComponents.Page;
import com.ucl.imaginethisserver.FigmaComponents.Style;

import java.io.IOException;
import java.util.List;

public class ButtonComponent extends FrontendComponent {
    /**
     * The text in the current button
     */
    private String character;
    private Style style;
    /**
     * The fills of the text
     */
    private List<Fills> TextFills;

    /**
     * The fills for the rectangle.
     */
    private List<Fills> RecFills;
    /**
     * The id of the wireframe that button is try to navigate to.
     */
    private String transitionNodeID;
    private double cornerRadius;
    private Color borderColor;
    private double borderWidth;
    /**
     * If the shape of the button is a circle.
     */
    private boolean isCircle = false;

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }


    public String getTransitionNodeID() {
        return transitionNodeID;
    }

    public void setTransitionNodeID(String transitionNodeID) {
        this.transitionNodeID = transitionNodeID;
    }

    public double getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }


    @Override
    public boolean isReusable() { return true; };

    @Override
    public String generateReusableCode() throws IOException {
        return readTemplateFile("Button.js");
    };

    @Override
    public String generateCode() {
        try {
            String backgroundColor = this.RecFills.get(0).getColor().toString();
            String textColor = this.TextFills.get(0).getColor().toString();
            StringBuilder buttonCode = new StringBuilder();
            buttonCode.append("<Button\n");
            if (this.transitionNodeID != null) {
                String navigateWireframe = ""; // TODO: page.getWireframeByID(transitionNodeID).getName();
                if(NavBarComponent.BUTTON_MAP.containsValue(navigateWireframe)){
                    buttonCode.append("onPress={() => this.props.navigation.navigate('NavigationBar', {screen:'" + navigateWireframe + "'})}\n");
                }else{
                    Navigator.NAVIGATOR_MAP.put(navigateWireframe,navigateWireframe);
                    buttonCode.append("onPress={() => this.props.navigation.navigate('").append(navigateWireframe).append("')}\n");
                }
            }
            buttonCode.append("   style={{backgroundColor:").append(backgroundColor).append(", marginTop: base.margin, minWidth: " + this.width);
            if (!isCircle) {
                buttonCode.append(", borderRadius: ").append(this.cornerRadius);
            }
            if (this.borderColor != null) {
                String borderColorStr = this.borderColor.toString();
                buttonCode.append(", borderColor: ").append(borderColorStr).append(" ,borderWidth: ").append(this.borderWidth);
            }
            buttonCode.append("}}\n");
            buttonCode.append("   textStyle={{color: ").append(textColor).append(", fontSize: ").append(this.style.getFontSize()).append("}}\n");
            if (isCircle) {
                buttonCode.append("circleDiameter={").append(this.width).append("}");
            }
            buttonCode.append(">").append(this.character).append("</Button>");

            return buttonCode.toString();
        }catch (Exception e){
            return "<P>The button component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }

    public void setTextFills(List<Fills> textFills) {
        TextFills = textFills;
    }

    public void setRecFills(List<Fills> recFills) {
        RecFills = recFills;
    }

    public String toString(){
        return this.height + " " + this.getPositionY();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setCircle(boolean circle) {
        isCircle = circle;
    }
}
