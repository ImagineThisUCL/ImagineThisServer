package com.ucl.imaginethisserver.FrontendComponents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *  Frontend Component Slider that contains
 *  the minimum value, the maximum value and current value of the slider.
*/
public class SliderComponent extends FrontendComponent {

    private final Logger logger = LoggerFactory.getLogger(SliderComponent.class);

    private int minValue;
    private int maxValue;
    private int currentValue;

    @Override
    public boolean isReusable() { return true; };

    @Override
    public String generateReusableCode() throws IOException {
        return readTemplateFile("CustomSlider.js");
    };

    @Override
    public String generateCode() {
        try {
                return  "                    <CustomSlider\n" +
                        "                        minimumTrackTintColor=\"#A4C8FF\"\n" +
                        "                        maximumTrackTintColor=\"#1A9AA9\"\n" +
                        "                        minimumValue={" + minValue + "}\n" +
                        "                        maximumValue={" + maxValue + "}\n" +
                        "                        step={1}\n" +
                        "                        value={" + currentValue + "}\n" +
                        "                        thumbTintColor=\"#3A334F\"\n" +
                        "                        labelStyle={{color: \"#C4C4C4\", fontWeight: \"bold\", fontSize: 21}}\n" +
                        "                        valueLabelStyle={{color: \"#1A9AA9\", fontWeight: \"bold\", fontSize: 21}}/>\n";
        }  catch (Exception e) {
            return "<P>The slider component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
}
