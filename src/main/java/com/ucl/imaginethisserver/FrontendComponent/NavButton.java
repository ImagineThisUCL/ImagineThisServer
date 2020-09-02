package com.ucl.imaginethisserver.FrontendComponent;

/**
 *  Navigation Button contains within the navigation bar,
 *  Works just like a image button.
*/
public class NavButton extends FrontendComponent{
    private String text;
    private String iconURL;
    private String transitionNodeID;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getTransitionNodeID() {
        return transitionNodeID;
    }

    public void setTransitionNodeID(String transitionNodeID) {
        this.transitionNodeID = transitionNodeID;
    }
}

