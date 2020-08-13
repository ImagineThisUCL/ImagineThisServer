package com.ucl.imaginethisserver.FrontendComponent;


import com.ucl.imaginethisserver.DAO.FigmaColor;
import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Form extends FrontendComponent{
    public ArrayList<FrontendComponent> frontendComponentList = new ArrayList<>();
    private FigmaColor backgroundColor;
    private FigmaColor borderColor;
    private double cornerRadius;
    private double borderWidth;
    private boolean isContainText, isContainButton, isContainTextBox,
            isContainImageButton, isContainImage, isContainChart,
            isContainDropdown, isContainSwitch;

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

    public String generateCode() throws IOException {
        StringBuilder code = new StringBuilder();
        String backgroundColorStr = "";
        if(this.backgroundColor == null){
            backgroundColorStr = "\"rgba(0,0,0,0)\"";
        }else{
            backgroundColorStr = this.backgroundColor.toString();
        }
        if(this.borderColor!=null){
            String borderColorStr = this.borderColor.toString();
            code.append("<View style={{borderRadius: " + this.cornerRadius + " , margin: 0, padding: 10, backgroundColor: " + backgroundColorStr +",borderColor: " + borderColorStr + ", borderWidth: " + borderWidth + "}}>\n");
        }else{
            code.append("<View style={{borderRadius: " + this.cornerRadius + " , margin: 0, padding: 10, backgroundColor: " + backgroundColorStr +"}}>").append("\n");
        }
        int preY = this.positionY;
        if(this.frontendComponentList.size() > 0) {
            ArrayList<List<FrontendComponent>> inlineComponentList = FrontendUtil.getInlineComponentList(this.frontendComponentList);
            for (List<FrontendComponent> curList : inlineComponentList) {
                if (curList.size() == 1) {
                    int marginTop = Math.max(curList.get(0).getPositionY() - preY, 0);
                    if(curList.get(0).getAlign().equals("RIGHT")){
                        code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + ", justifyContent: \"flex-end\"}}>\n");
                    }else if(curList.get(0).getAlign().equals("CENTER")){
                        code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + ", justifyContent: \"center\"}}>\n");
                    }else{
                        code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + "}}>\n");
                    }
                    code.append(curList.get(0).generateCode()).append("\n");
                    code.append("</View>\n");
                    preY = curList.get(0).getPositionY() + curList.get(0).getHeight();
                } else if (curList.size() > 1) {
                    int minY = Integer.MAX_VALUE;
                    int maxY = -1;
                    for(FrontendComponent component : curList){
                        if(component.getPositionY() < minY){
                            minY = component.getPositionY();
                        }
                    }
                    int marginTop = Math.max(minY - preY, 0);
                    code.append("<View style={{flexDirection: 'row', justifyContent: \"space-between\", marginTop: " + marginTop +"}}>\n");
                    for (FrontendComponent component : curList) {
                        int flex = Math.max((int)(((double)component.width)/((double)this.width) * 10) , 1);

                        component.setFlex(flex);
                        code.append("<View style={{flex: " + flex + "}}>\n");
                        code.append(component.generateCode()).append("\n");
                        code.append("</View>\n");
                        if(component.getPositionY() + component.getHeight() > maxY){
                            maxY = component.getPositionY() + component.getHeight();
                        }
                    }
                    code.append("</View>\n");
                    preY = maxY;
                }
            }
        }
        code.append("</View>\n");
        return code.toString();
    }

    public void setContainText(boolean containText) {
        isContainText = containText;
    }

    public void setContainButton(boolean containButton) {
        isContainButton = containButton;
    }

    public void setContainTextBox(boolean containTextBox) {
        isContainTextBox = containTextBox;
    }

    public void setContainImageButton(boolean containImageButton) {
        isContainImageButton = containImageButton;
    }

    public void setContainImage(boolean containImage) {
        isContainImage = containImage;
    }

    public void setContainChart(boolean containChart) {
        isContainChart = containChart;
    }

    public void setContainDropdown(boolean containDropdown) {
        isContainDropdown = containDropdown;
    }

    public void setContainSwitch(boolean containSwitch) {
        isContainSwitch = containSwitch;
    }

    public void setBorderColor(FigmaColor borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public boolean isContainText() {
        return isContainText;
    }

    public boolean isContainButton() {
        return isContainButton;
    }

    public boolean isContainTextBox() {
        return isContainTextBox;
    }

    public boolean isContainImageButton() {
        return isContainImageButton;
    }
    public boolean isContainImage() {
        return isContainImage;
    }
    public boolean isContainChart() {
        return isContainChart;
    }
    public boolean isContainDropdown() {
        return isContainDropdown;
    }
    public boolean isContainSwitch() {
        return isContainSwitch;
    }
}
