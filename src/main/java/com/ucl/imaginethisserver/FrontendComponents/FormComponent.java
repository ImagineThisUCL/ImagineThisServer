package com.ucl.imaginethisserver.FrontendComponents;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class FormComponent extends GroupComponent {

    private final Logger logger = LoggerFactory.getLogger(FormComponent.class);

    @Override
    public boolean requiresReusableComponent() { return false; };

    @Override
    public String getReusableComponentName() { return null; };

    /**
     *  Function that writes the code of the form,
     *  what it is doing is basically deciding which kind of View wrap to use.
    */
    @Override
    public String generateCode() {
        try {
            StringBuilder code = new StringBuilder();
            String backgroundColorStr;
            if (getBackgroundColor() == null) {
                backgroundColorStr = "\"rgba(0,0,0,0)\"";
            } else {
                backgroundColorStr = getBackgroundColor().toString();
            }
            if (getBorderColor() != null) {
                String borderColorStr = getBorderColor().toString();
                code.append("<View style={{borderRadius: " + getCornerRadius() + ", padding: 10, flex: 1, backgroundColor: " + backgroundColorStr + ",borderColor: " + borderColorStr + ", borderWidth: " + getBorderWidth() + "}}>\n");
            } else {
                code.append("<View style={{borderRadius: " + getCornerRadius() + ", padding: 10, flex: 1, backgroundColor: " + backgroundColorStr + "}}>\n");
            }
            int preY = getPositionY();
            if (getComponents().size() > 0) {
                List<List<FrontendComponent>> inlineComponentList = FrontendComponent.getInlineComponentList(getComponents());
                for (List<FrontendComponent> curList : inlineComponentList) {
                    if (curList.size() == 1) {
                        FrontendComponent component = curList.get(0);
                        int marginTop = Math.max(curList.get(0).getPositionY() - preY, 0);
                        if (component.getAlign().equals("RIGHT")) {
                            code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + ", justifyContent: 'flex-end'}}>\n");
                        } else if (component.getAlign().equals("CENTER")) {
                            code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + ", justifyContent: 'center'}}>\n");
                        } else {
                            code.append("<View style={{flexDirection: 'row', marginTop: " + marginTop + "}}>\n");
                        }
                        code.append(component.generateCode()).append("\n");
                        code.append("</View>\n");
                        preY = component.getPositionY() + component.getHeight();

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
                            int flex = Math.max((int) (((double) component.getWidth()) / ((double) getWidth()) * 10), 1);
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

        } catch (Exception e) {
            logger.error("Could not generate Form component.");
            e.printStackTrace();
            return "<View>\n" +
                    "    <P>The form component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" +
                    "</View>\n";
        }
    }


}
