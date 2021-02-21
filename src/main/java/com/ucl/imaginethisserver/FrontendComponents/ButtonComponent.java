package com.ucl.imaginethisserver.FrontendComponents;

public class ButtonComponent extends FrontendComponent {

    private String characters;
    private String transitionNodeID;
    private String transitionNodeName;
    private boolean isCircle = false;

    public String getCharacters() {
        return characters;
    }

    public String getTransitionNodeID() {
        return transitionNodeID;
    }

    public String getTransitionNodeName() {
        return transitionNodeName;
    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public void setTransitionNodeID(String transitionNodeID) {
        this.transitionNodeID = transitionNodeID;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public void setTransitionNodeName(String transitionNodeName) {
        this.transitionNodeName = transitionNodeName;
    }

    @Override
    public String getReusableComponentName() { return "Button.js"; }

    @Override
    public String generateCode() {
        try {
            String backgroundColor = getContainerFills().get(0).getColor().toString();
            String textColor = getTextFills().get(0).getColor().toString();
            StringBuilder buttonCode = new StringBuilder();
            buttonCode.append("<Button\n");
            if (transitionNodeID != null) {
                buttonCode.append("onPress={() => this.props.navigation.navigate('" + transitionNodeName + "')}\n");
            }
            buttonCode.append("   style={{backgroundColor:").append(backgroundColor).append(", marginTop: base.margin, minWidth: " + getWidth());
            if (!isCircle) {
                buttonCode.append(", borderRadius: ").append(getCornerRadius());
            }
            if (getBorderColor() != null) {
                String borderColorStr = getBorderColor().toString();
                buttonCode.append(", borderColor: ").append(borderColorStr).append(" ,borderWidth: ").append(getBorderWidth());
            }
            buttonCode.append("}}\n");
            buttonCode.append("   textStyle={{color: ").append(textColor).append(", fontSize: ").append(getStyle().getFontSize()).append("}}\n");
            if (isCircle) {
                buttonCode.append("circleDiameter={").append(getWidth()).append("}");
            }
            buttonCode.append(">").append(this.characters).append("</Button>");

            return buttonCode.toString();
        } catch (Exception e) {
            return "<P>The button component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }

}
