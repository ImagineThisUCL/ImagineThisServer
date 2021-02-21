package com.ucl.imaginethisserver.FrontendComponents;


public class NavigationButtonComponent extends ImageButtonComponent {

    private String text;

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    @Override
    public boolean requiresReusableComponent() { return false; }

    @Override
    public String getReusableComponentName() { return null; }

    @Override
    public String generateCode() {
        return null;
    }

}
