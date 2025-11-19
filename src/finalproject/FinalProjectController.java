/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package finalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Yasmine and Jacques
 */
public class FinalProjectController implements Initializable {
    private LineChart[] charts = new LineChart[2];
    
    @FXML
    private HBox graphHBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCharts();
    }
    
    /**
     * Initializes the frequency charts to the charts variable, including axis and series.
     */
    public void initializeCharts() {
        for (int i = 0; i < 2; i++) {
            NumberAxis timeAxis = new NumberAxis(); timeAxis.setLabel("Time (s)");
            NumberAxis frequencyAxis = new NumberAxis(); frequencyAxis.setLabel("Frequency (Hz)");
            
            LineChart lineChart = new LineChart(timeAxis, frequencyAxis);
            lineChart.setTitle("Frequency Observed by " + (char)(65 + i));
            lineChart.setCreateSymbols(false);
            lineChart.setLegendVisible(false);
            
            Series series = new XYChart.Series<>();
            
            lineChart.getData().add(series);
            
            charts[i] = lineChart;
            graphHBox.getChildren().add(lineChart);
        }
    }
    
    public void addPoint(int chartIndex, double time, double frequency) {
        Series series = (Series) charts[chartIndex].getData().get(0);
        series.getData().add(new XYChart.Data<>(time, frequency));
    }
}
