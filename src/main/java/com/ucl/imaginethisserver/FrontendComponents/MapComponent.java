package com.ucl.imaginethisserver.FrontendComponents;

import java.io.IOException;

public class MapComponent extends FrontendComponent {

    @Override
    public boolean isReusable() { return true; };

    @Override
    public String generateReusableCode() throws IOException {
        return readTemplateFile("GoogleMap.js");
    };

    @Override
    public String generateCode(){
        return "<GoogleMap />";
    }
}
