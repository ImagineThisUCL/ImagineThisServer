package com.ucl.imaginethisserver.FrontendComponents;

public class RectangleComponent extends FrontendComponent {

    @Override
    public boolean isReusable() { return false; };

    @Override
    public String generateReusableCode() { return null; };

    /**
     *  Function that writes the code of the form,
     *  what it is doing is basically deciding which kind of View wrap to use.
     */
    @Override
    public String generateCode() { return null; };

}
