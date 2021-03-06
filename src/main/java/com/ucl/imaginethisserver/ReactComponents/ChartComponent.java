package com.ucl.imaginethisserver.ReactComponents;

public class ChartComponent extends ReactComponent {

    @Override
    public String getReusableComponentName() { return "Chart.js"; }

    @Override
    public String generateCode() {
        try {
            StringBuilder code = new StringBuilder();
            code.append("<View style={{padding: 10, backgroundColor: \"#ffffff\", borderRadius: 10, marginBottom: 10}}>\n" +
                    "<LineChart\n" +
                    "data={LINE_CHART_DATA}\n" +
                    "width={" + getWidth() + "}\n" +
                    "height={" + getHeight() + "}\n" +
                    "chartConfig={LINE_CHART_CONFIG}\n" +
                    "bezier/>\n" +
                    "</View>\n");
            return code.toString();
        } catch (Exception e) {
            return "<P>The chart component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }
}
