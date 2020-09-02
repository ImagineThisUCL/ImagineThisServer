package com.ucl.imaginethisserver.Component;

public class FixedChartComponent {
    /**
     *  Function contains fixed code content of Data required within the chart of the frontend
     *  You can change these data directly by editing the generated file.
     */
    public static String generateCode(){
        return "const LINE_CHART_DATA = {\n" +
                "            labels: [\"Jan.\", \"Feb.\", \"Mar.\", \"Apr.\", \"May\", \"Jun.\"],\n" +
                "            datasets: [\n" +
                "                {\n" +
                "                data: [4, 6, 5, 9, 1, 5],\n" +
                "                color: (opacity = 1) => `rgba(26, 154, 169, ${opacity})`, // optional\n" +
                "                strokeWidth: 2 // optional\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "\n" +
                "        const LINE_CHART_CONFIG = {\n" +
                "            backgroundGradientFrom: \"#ffffff\",\n" +
                "            backgroundGradientFromOpacity: 1,\n" +
                "            backgroundGradientTo: \"#ffffff\",\n" +
                "            backgroundGradientToOpacity: 1,\n" +
                "            color: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,\n" +
                "            strokeWidth: 2, // optional, default 3\n" +
                "            barPercentage: 0.5,\n" +
                "            useShadowColorFromDataset: true // optional\n" +
                "        }\n";
    }
}
