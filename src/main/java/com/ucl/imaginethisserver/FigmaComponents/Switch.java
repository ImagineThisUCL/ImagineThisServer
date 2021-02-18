package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.SwitchComponent;

public class Switch extends FigmaComponent {


    public SwitchComponent convertToFrontendComponent() {
        SwitchComponent aSwitch = new SwitchComponent();
        aSwitch.setPositionX(this.getPositionX());
        aSwitch.setPositionY(this.getPositionY());
        aSwitch.setWidth(this.getWidth());
        aSwitch.setHeight(this.getHeight());
        aSwitch.setAlign(this.getAlign());
        return aSwitch;
    }


}
