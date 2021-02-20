package com.ucl.imaginethisserver.FrontendComponents;

public class RectangleComponent extends FrontendComponent {

    @Override
    public boolean requiresReusableComponent() { return false; };

    @Override
    public String getReusableComponentName() { return null; };

    /**
     *  Function that writes the code of the form,
     *  what it is doing is basically deciding which kind of View wrap to use.
     */
    @Override
    public String generateCode() { return null; };

}
