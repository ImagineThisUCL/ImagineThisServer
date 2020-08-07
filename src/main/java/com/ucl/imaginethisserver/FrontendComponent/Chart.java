package com.ucl.imaginethisserver.FrontendComponent;

public class Chart extends FrontendComponent {
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String generateCode(){
        StringBuilder code = new StringBuilder();
        code.append("<LineChart\n" +
                "data={LINE_CHART_DATA}\n" +
                "width={CHART_WIDTH}\n" +
                "height={256}\n" +
                "chartConfig={LINE_CHART_CONFIG}\n" +
                "bezier/>");
        return code.toString();
    }
}
