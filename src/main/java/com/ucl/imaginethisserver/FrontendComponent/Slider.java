package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.FigmaColor;

public class Slider extends FrontendComponent {
    private int min_value;
    private int max_value;
    private int cur_value;

    public String generateCode(){
        try {
                return  "                    <CustomSlider\n" +
                        "                        minimumTrackTintColor=\"#A4C8FF\"\n" +
                        "                        maximumTrackTintColor=\"#1A9AA9\"\n" +
                        "                        minimumValue={" + this.min_value + "}\n" +
                        "                        maximumValue={" + this.max_value + "}\n" +
                        "                        step={1}\n" +
                        "                        value={" + this.cur_value + "}\n" +
                        "                        thumbTintColor=\"#3A334F\"\n" +
                        "                        labelStyle={{color: \"#C4C4C4\", fontWeight: \"bold\", fontSize: 21}}\n" +
                        "                        valueLabelStyle={{color: \"#1A9AA9\", fontWeight: \"bold\", fontSize: 21}}/>\n";
        }catch (Exception e){
            return "<P>The slider component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }

    public void setCur_value(int cur_value) {
        this.cur_value = cur_value;
    }

    public void setMax_value(int max_value) {
        this.max_value = max_value;
    }

    public void setMin_value(int min_value) {
        this.min_value = min_value;
    }
}
