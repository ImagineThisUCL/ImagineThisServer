package com.ucl.imaginethisserver.FrontendComponents;

import java.io.IOException;

public class MapComponent extends FrontendComponent {

    @Override
    public boolean requiresReusableComponent() { return true; };

    @Override
    public String getReusableComponentName() { return "GoogleMap.js"; };

    @Override
    public String generateCode(){
        return "<GoogleMap />";
    }
}
