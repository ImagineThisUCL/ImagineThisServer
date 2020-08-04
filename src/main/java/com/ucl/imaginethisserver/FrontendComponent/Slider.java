package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.FigmaColor;

public class Slider extends FrontendComponent {
    private int min_value;
    private int max_value;
    private int cur_value;
    private FigmaColor backgroundColor;
    private double borderRadius;

    public String generateCode(){
        if(backgroundColor != null){
            return "<View style={{padding: 10, backgroundColor: " + backgroundColor.toString() + ", borderRadius: " + this.borderRadius +"}}>\n" +
                    "                    <CustomSlider\n" +
                    "                        minimumTrackTintColor=\"#A4C8FF\"\n" +
                    "                        maximumTrackTintColor=\"#1A9AA9\"\n" +
                    "                        minimumValue={" + this.min_value + "}\n" +
                    "                        maximumValue={" + this.max_value + "}\n" +
                    "                        step={1}\n" +
                    "                        value={" + this.cur_value + "}\n" +
                    "                        thumbTintColor=\"#3A334F\"\n" +
                    "                        labelStyle={{color: \"#C4C4C4\", fontWeight: \"bold\", fontSize: 21}}\n" +
                    "                        valueLabelStyle={{color: \"#1A9AA9\", fontWeight: \"bold\", fontSize: 21}}/>\n" +
                    "                </View>\n";
        }else{
            return "<View style={{padding: 10}}>\n" +
                    "                    <CustomSlider\n" +
                    "                        minimumTrackTintColor=\"#A4C8FF\"\n" +
                    "                        maximumTrackTintColor=\"#1A9AA9\"\n" +
                    "                        minimumValue={" + this.min_value + "}\n" +
                    "                        maximumValue={" + this.max_value + "}\n" +
                    "                        step={1}\n" +
                    "                        value={" + this.cur_value + "}\n" +
                    "                        thumbTintColor=\"#3A334F\"\n" +
                    "                        labelStyle={{color: \"#C4C4C4\", fontWeight: \"bold\", fontSize: 21}}\n" +
                    "                        valueLabelStyle={{color: \"#1A9AA9\", fontWeight: \"bold\", fontSize: 21}}/>\n" +
                    "                </View>\n";
        }
    }

    public void setCur_value(int cur_value) {
        this.cur_value = cur_value;
    }

    public void setBackgroundColor(FigmaColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderRadius(double borderRadius) {
        this.borderRadius = borderRadius;
    }

    public void setMax_value(int max_value) {
        this.max_value = max_value;
    }

    public void setMin_value(int min_value) {
        this.min_value = min_value;
    }
}