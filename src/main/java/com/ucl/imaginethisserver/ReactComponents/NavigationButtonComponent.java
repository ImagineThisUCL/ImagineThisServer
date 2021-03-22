package com.ucl.imaginethisserver.ReactComponents;


public class NavigationButtonComponent extends ImageButtonComponent {

    private String text;

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    @Override
    public String getReusableComponentName() { return null; }

    @Override
    public String generateCode() {
        return null;
    }

}
