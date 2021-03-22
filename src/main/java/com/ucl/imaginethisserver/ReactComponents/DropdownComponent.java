package com.ucl.imaginethisserver.ReactComponents;

public class DropdownComponent extends ReactComponent {

    // First default option of the dropdown.
    private String option;
    public void setOption(String string) {
        option = string;
    }

    @Override
    public String getReusableComponentName() { return "Dropdown.js"; }

    @Override
    public String generateCode(){
        try {
            StringBuilder code = new StringBuilder();
            String textColor = getTextFills().get(0).getColor().toString();
            String containerColor = getContainerFills().get(0).getColor().toString();

            code.append("<Dropdown\n" +
                    "items={[\n" +
                    "{key: 'option1', label: '");
            code.append(this.option);
            code.append("'},\n" +
                    "{key: 'option2', label: 'Placeholder'},\n" +
                    "]}\n");
            code.append("containerStyle={{borderRadius:" + getCornerRadius() + ", backgroundColor: " + containerColor + "}}\n");
            code.append("textStyle={{fontSize: " + getStyle().getFontSize() + ", color: " + textColor + "}}/>\n");
            return code.toString();
        } catch (Exception e) {
            return "<P>The drop down component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }
}
