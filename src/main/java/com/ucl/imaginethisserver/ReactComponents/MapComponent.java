package com.ucl.imaginethisserver.ReactComponents;


public class MapComponent extends ReactComponent {

    @Override
    public String getReusableComponentName() { return "GoogleMap.js"; }

    @Override
    public String generateCode(){
        return "<GoogleMap />";
    }

}
