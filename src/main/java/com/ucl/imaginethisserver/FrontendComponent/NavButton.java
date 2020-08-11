package com.ucl.imaginethisserver.FrontendComponent;

public class NavButton extends FrontendComponent{
    private String text;
    private String iconURL;

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
}

