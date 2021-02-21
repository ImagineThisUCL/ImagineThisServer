package com.ucl.imaginethisserver.FrontendComponents;


public class MapComponent extends FrontendComponent {

    @Override
    public String getReusableComponentName() { return "GoogleMap.js"; }

    @Override
    public String generateCode(){
        return "<GoogleMap />";
    }

}
