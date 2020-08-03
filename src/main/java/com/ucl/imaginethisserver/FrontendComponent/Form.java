package com.ucl.imaginethisserver.FrontendComponent;


import com.ucl.imaginethisserver.DAO.FigmaColor;
import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Form extends FrontendComponent{
    public ArrayList<FrontendComponent> frontendComponentList = new ArrayList<>();
    private FigmaColor backgroundColor;
    private double cornerRadius;

    public FigmaColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(FigmaColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public double getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void sortComponentByY(){
        frontendComponentList.sort(new Comparator<FrontendComponent>() {
            @Override
            public int compare(FrontendComponent o1, FrontendComponent o2) {
                if (o1.getPositionY() > o2.getPositionY()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    public String generateCode(){
        StringBuilder code = new StringBuilder();
        code. append("<View containerStyle={{borderRadius: " + this.cornerRadius + " , margin: 0, padding: 10, backgroundColor: " + this.backgroundColor.toString() +"}}>").append("\n");

        ArrayList<List<FrontendComponent>> inlineComponentList = FrontendUtil.getInlineComponentList(this.frontendComponentList);
        for(List<FrontendComponent> curList : inlineComponentList){
            code.append("<View style={{flexDirection: 'row'}}>\n");
            for(FrontendComponent component: curList){
                code.append(component.generateCode()).append("\n");
            }
            code.append("</View>\n");
        }
        code.append("</View>\n");
        return code.toString();
    }
}
