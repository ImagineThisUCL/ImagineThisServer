package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.FigmaColor;
import com.ucl.imaginethisserver.DAO.Fills;
import com.ucl.imaginethisserver.DAO.Style;

import java.util.List;

public class TextBox extends FrontendComponent {
    private String placeholder = null;
    private String label = null;
    private Style style;
    private List<Fills> containerFills;
    private List<Fills> labelFills;
    private List<Fills> TextFills;
    private double cornerRadius;

    public double getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setPlaceholder(String character) {
        this.placeholder = character;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setContainerFills(List<Fills> conFills) {
        containerFills = conFills;
    }

    public void setTextFills(List<Fills> textFills) {
        TextFills = textFills;
    }

    public String generateCode() {
        try {
            //default color for background:  transparent;
            String containerColor = new FigmaColor(0.f, 0.f, 0.f, 0.f).toString();
            //default color for label and text:  black;
            String labelColor = new FigmaColor(0, 0, 0, 1).toString();
            String textColor = new FigmaColor(0, 0, 0, 1).toString();
            if (containerFills.size() > 0) {
                containerColor = this.containerFills.get(0).getColor().toString();
            }
            if (TextFills.size() > 0) {
                textColor = this.TextFills.get(0).getColor().toString();
            }
            StringBuilder textBoxCode = new StringBuilder();
            textBoxCode.append("<InputField\n");

            if (label != null) {
                textBoxCode.append("     label='").append(this.label).append("'\n");
                textBoxCode.append("     labelStyle={{color: " + labelColor + ", marginBottom: 5}}\n");
            }
            if (placeholder != null) {
                textBoxCode.append("     placeholder='").append(this.placeholder).append("'\n");
            }

            textBoxCode.append("     inputStyle={{color: " + textColor + "}}\n");
            textBoxCode.append(
                "     inputContainerStyle={{\n" +
                "           borderWidth: 1,\n" +
                "           borderRadius: " + this.getCornerRadius() + ",\n" +
                "           paddingHorizontal: 10,\n" +
                "           backgroundColor: "+ containerColor +",}}\n"
                );
            textBoxCode.append("/>");

            return textBoxCode.toString();

        } catch (Exception e) {
            return "<P>The input field component could not be generated, please check your Figma file and refer to our guidelines.</P>\n";
        }
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
