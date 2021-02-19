package com.ucl.imaginethisserver.FrontendComponents;

import java.io.IOException;

public class NavigationButtonComponent extends ImageButtonComponent {

    private String text;

    public String getText() { return text; };

    public void setText(String text) { this.text = text; };

    @Override
    public boolean isReusable() { return false; };

    @Override
    public String generateReusableCode() throws IOException {
        return null;
    };

    @Override
    public String generateCode() {
        return null;
    }

}
