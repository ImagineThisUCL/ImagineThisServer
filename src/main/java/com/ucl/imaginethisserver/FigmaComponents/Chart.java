package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.ReactComponents.ChartComponent;

public class Chart extends Group {

    /**
     *  @return Function used to convert a Group component into a Fixed chart
     *  With nothing within the chart can be recognized under the current version.
     *  Only a fixed content chart will be generated at the position of the component.
     */
    @Override
    public ChartComponent convertToFrontendComponent() {
        ChartComponent fixedChartComponent = new ChartComponent();
        fixedChartComponent.setHeight(this.getHeight());
        fixedChartComponent.setWidth(this.getWidth());
        fixedChartComponent.setPositionX(this.getPositionX());
        fixedChartComponent.setPositionY(this.getPositionY());
        fixedChartComponent.setAlign(this.getAlign());

        return fixedChartComponent;
    }

}
