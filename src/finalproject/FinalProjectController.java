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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;


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
        
        for (int i = 0; i < 2; i++) {
            NumberAxis timeAxis = new NumberAxis(); timeAxis.setLabel("Time (s)");
            NumberAxis pressureAxis = new NumberAxis(); pressureAxis.setLabel("Pressure (kPa)");
            
            LineChart lineChart = new LineChart(timeAxis, pressureAxis);
            lineChart.setTitle("Pressure observed by " + (char)(65 + i));
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
     @FXML
    private TitledPane entityBTitlePane, entityATitlePane;

    @FXML
    private Label accelerationALabel, accelerationBLabel, positionALabel, positionBLabel, velocityALabel, velocityBLabel;

    @FXML
    private Slider accelerationASlider, accelerationBSlider, positionASlider, positionBSlider, velocityASlider, velocityBSlider;

    //Entity variables
    Entity entityA;
    Entity entityB;    
 
    /**
     * handle the velocity of the entity B
     * @param event the mouseEvent
     */
    @FXML
    public void handleVelocityB(MouseEvent event) {
        entityB.setVelocity((double) velocityBSlider.getUserData());
    }
    
    /**
     * handle the velocity of entity A
     * @param event the mouseEvent
     */
     @FXML
    void handleVelocityA(MouseEvent event) {
        entityA.setVelocity((double) velocityASlider.getUserData());
    }
    
    /**
     * handle the acceleration of entity A
     * @param event the mouseEvent
     */
     @FXML
    void handleAccelerationA(MouseEvent event) {
        entityA.setAcceleration((double) accelerationASlider.getUserData());
    }

    /**
     * handle the acceleration of entity B
     * @param event the mouseEvent
     */
    @FXML
    void handleAccelerationB(MouseEvent event) {
        entityB.setAcceleration((double) accelerationBSlider.getUserData());
    }

    /**
     * handle the position of entity A
     * @param event 
     */
    @FXML
    void handlePositionA(MouseEvent event) {
        entityA.setPosition((double) positionASlider.getUserData());
    }

    /**
     * handle the position of entity B
     * @param event 
     */
    @FXML
    void hundlePositionB(MouseEvent event) {
        entityB.setPosition((double) positionBSlider.getUserData());
    }
}
