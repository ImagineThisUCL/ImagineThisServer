package com.ucl.imaginethisserver.ReactComponents;

public class ButtonComponent extends ReactComponent {

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
            String backgroundColor;
            // If no color is chosen, have transparent background
            if (getContainerFills().isEmpty()) {
                backgroundColor = "rgba(255,255,255,0)";
            } else {
                backgroundColor = getContainerFills().get(0).getColor().toString();
            }
            String textColor = getTextFills().get(0).getColor().toString();
            StringBuilder buttonCode = new StringBuilder();
            buttonCode.append("<Button\n");
            if (transitionNodeID != null) {
                buttonCode.append("onPress={() => this.props.navigation.navigate('" + transitionNodeName + "')}\n");
            }
            buttonCode.append(String.format("style={{backgroundColor: '%s', marginTop: base.margin, minWidth: %d, ", backgroundColor, getWidth()));
            if (!isCircle) {
                buttonCode.append(String.format("borderRadius: %f, ", getCornerRadius()));
            }
            if (getBorderColor() != null) {
                String borderColorStr = getBorderColor().toString();
                buttonCode.append(String.format("borderColor: %s, borderWidth: %f", borderColorStr, getBorderWidth()));
            }
            buttonCode.append("}}\n");
            buttonCode.append(String.format("textStyle={{color: %s, fontSize: %f}}\n", textColor, getStyle().getFontSize()));
            if (isCircle) {
                buttonCode.append(String.format("circleDiameter={%d}", getWidth()));
            }
            buttonCode.append(String.format(">%s</Button>", this.characters));
            return buttonCode.toString();

        } catch (Exception e) {
            return "<P>The button component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }

}
