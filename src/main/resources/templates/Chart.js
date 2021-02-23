export const LINE_CHART_DATA = {
    labels: ["Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun."],
    datasets: [
        {
            data: [4, 6, 5, 9, 1, 5],
            color: (opacity = 1) => `rgba(26, 154, 169, ${opacity})`, // optional
            strokeWidth: 2 // optional
        }
    ]
}

export const LINE_CHART_CONFIG = {
    backgroundGradientFrom: "#ffffff",
    backgroundGradientFromOpacity: 1,
    backgroundGradientTo: "#ffffff",
    backgroundGradientToOpacity: 1,
    color: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
    strokeWidth: 2, // optional, default 3
    barPercentage: 0.5,
    useShadowColorFromDataset: true // optional
}
