package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.FigmaComponents.Color;
import com.ucl.imaginethisserver.FigmaComponents.Paint;

import java.util.List;


public class TextBoxComponent extends FrontendComponent {

    private String placeholder = null;
    private String label = null;
    private List<Paint> labelFills;

    public void setPlaceholder(String character) {
        this.placeholder = character;
    }

    @Override
    public boolean requiresReusableComponent() { return true; }

    @Override
    public String getReusableComponentName() { return "InputField.js"; }

    @Override
    public String generateCode() {
        try {
            String containerColor = new Color(0.f, 0.f, 0.f, 0.f).toString();
            String labelColor = new Color(0.f, 0.f, 0.f, 1.f).toString();
            String textColor = new Color(0.f, 0.f, 0.f, 1.f).toString();

            if (!getContainerFills().isEmpty()) {
                containerColor = getContainerFills().get(0).getColor().toString();
            }

            if (!getTextFills().isEmpty()) {
                textColor = getTextFills().get(0).getColor().toString();
            }

            StringBuilder textBoxCode = new StringBuilder();
            textBoxCode.append("<InputField\n");

            if (label != null) {
                if(!labelFills.isEmpty()){
                    labelColor = labelFills.get(0).getColor().toString();
                }
                textBoxCode.append("     label='" + label + "'\n");
                textBoxCode.append("     labelStyle={{color: " + labelColor + ", marginBottom: 5}}\n");
            }
            if (placeholder != null) {
                textBoxCode.append("     placeholder='").append(placeholder).append("'\n");
            }

            textBoxCode.append("     inputStyle={{color: " + textColor + "}}\n");
            textBoxCode.append(
                "     inputContainerStyle={{\n" +
                "           borderWidth: 1,\n" +
                "           borderRadius: " + getCornerRadius() + ",\n" +
                "           paddingHorizontal: 10,\n" +
                "           backgroundColor: "+ containerColor +",}}\n"
                );
            textBoxCode.append("/>");

            return textBoxCode.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "<P>The input field component could not be generated, please check your Figma file and refer to our guidelines.</P>\n";
        }
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLabelFills(List<Paint> labelFills) {
        this.labelFills = labelFills;
    }
}
