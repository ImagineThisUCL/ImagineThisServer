package com.ucl.imaginethisserver.FrontendComponents;


import com.ucl.imaginethisserver.FigmaComponents.Color;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class FormComponent extends FrontendComponent {
    /**
     * All of the frontend components that are included in the current form.
     */
    public ArrayList<FrontendComponent> frontendComponentList = new ArrayList<>();
    private Color backgroundColor;
    private Color borderColor;
    private double cornerRadius;
    private double borderWidth;
//    If the current form contains the following frontend components.
    private boolean isContainText, isContainButton, isContainTextBox,
            isContainImageButton, isContainImage, isContainChart,
            isContainDropdown, isContainSwitch, isContainSlider;
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public double getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }
    /**
     *  Sort the components within the form to decide their vertical positioning
    */
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
    /**
     *  Function that writes the code of the form,
     *  what it is doing is basically deciding which kind of View wrap to use.
    */
    public String generateCode() throws IOException {
        try {
            StringBuilder code = new StringBuilder();
            String backgroundColorStr = "";
            if (this.backgroundColor == null) {
                backgroundColorStr = "\"rgba(0,0,0,0)\"";
            } else {
                backgroundColorStr = this.backgroundColor.toString();
            }
            if (this.borderColor != null) {
                String borderColorStr = this.borderColor.toString();
                code.append("<View style={{borderRadius: " + this.cornerRadius + ", padding: 10, flex: 1, backgroundColor: " + backgroundColorStr + ",borderColor: " + borderColorStr + ", borderWidth: " + borderWidth + "}}>\n");
            } else {
                code.append("<View style={{borderRadius: " + this.cornerRadius + ", padding: 10, flex: 1, backgroundColor: " + backgroundColorStr + "}}>").append("\n");
            }
            int preY = this.positionY;
            if (this.frontendComponentList.size() > 0) {
                ArrayList<List<FrontendComponent>> inlineComponentList = FrontendUtil.getInlineComponentList(this.frontendComponentList);
                for (List<FrontendComponent> curList : inlineComponentList) {
                    if (curList.size() == 1) {
                        int marginTop = Math.max(curList.get(0).getPositionY() - preY, 0);
                        if (curList.get(0).getAlign().equals("RIGHT")) {
                            code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + ", justifyContent: \"flex-end\"}}>\n");
                        } else if (curList.get(0).getAlign().equals("CENTER")) {
                            code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + ", justifyContent: \"center\"}}>\n");
                        } else {
                            code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + "}}>\n");
                        }
                        code.append(curList.get(0).generateCode()).append("\n");
                        code.append("</View>\n");
                        preY = curList.get(0).getPositionY() + curList.get(0).getHeight();
                    } else if (curList.size() > 1) {
                        int minY = Integer.MAX_VALUE;
                        int maxY = -1;
                        for (FrontendComponent component : curList) {
                            if (component.getPositionY() < minY) {
                                minY = component.getPositionY();
                            }
                        }
                        int marginTop = Math.max(minY - preY, 0);
                        code.append("<View style={{flexDirection: 'row', justifyContent: \"space-between\", marginTop: " + marginTop + "}}>\n");
                        for (FrontendComponent component : curList) {
                            int flex = Math.max((int) (((double) component.width) / ((double) this.width) * 10), 1);
                            component.setFlex(flex);
                            code.append("<View style={{flex: " + flex + "}}>\n");
                            code.append(component.generateCode()).append("\n");
                            code.append("</View>\n");
                            if (component.getPositionY() + component.getHeight() > maxY) {
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
        }catch (Exception e){
            e.printStackTrace();
            return "<View>\n" +
                    "    <P>The form component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" +
                    "</View>\n";
        }
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

    public void setBorderColor(Color borderColor) {
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

    public void setContainSlider(boolean containSlider) {
        isContainSlider = containSlider;
    }

    public boolean isContainSlider() {
        return isContainSlider;
    }
}
