package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.SwitchComponent;

public class Switch extends FigmaComponent {

    @Override
    public SwitchComponent convertToFrontendComponent() {
        SwitchComponent aSwitch = new SwitchComponent();
        aSwitch.setPositionX(getPositionX());
        aSwitch.setPositionY(getPositionY());
        aSwitch.setWidth(getWidth());
        aSwitch.setHeight(getHeight());
        aSwitch.setAlign(getAlign());
        return aSwitch;
    }


}
