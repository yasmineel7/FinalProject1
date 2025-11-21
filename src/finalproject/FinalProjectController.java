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
    
    
    //Entity variables
    double velocity = 20; //variable to change
    double time = 100; //variable to change
    Entity entityA;
    Entity entityB;
    
    DopplerModel model = new DopplerModel(velocity, entityA, entityB, time);
    
    @FXML
    private HBox graphHBox;
    
    @FXML
    private TitledPane entityBTitlePane, entityATitlePane;

    @FXML
    private Label accelerationALabel, accelerationBLabel, positionALabel, positionBLabel, velocityALabel, velocityBLabel;

    @FXML
    private Slider accelerationASlider, accelerationBSlider, positionASlider, positionBSlider, velocityASlider, velocityBSlider;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCharts();
        double position = 5;
        double velocity = 20;
        double acceleration = 3;
        double observedFrequency = 20;
        double sourceFrequency1 = 10;
        double sourcefrequency2 = 0;
        entityA = new Entity(position, velocity, acceleration, 0, 20);
        entityB = new Entity(position, velocity, acceleration, sourceFrequency1, observedFrequency);
        
        model.update(time);
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
 
    /**
     * handle the velocity of the entity B
     * @param event the mouseEvent
     */
    @FXML
    public void handleVelocityB(MouseEvent event) {
        entityB.setVelocity((double) velocityBSlider.getValue());
    }
    
    /**
     * handle the velocity of entity A
     * @param event the mouseEvent
     */
     @FXML
    void handleVelocityA(MouseEvent event) {
        entityA.setVelocity((double) velocityASlider.getValue());
    }
    
    /**
     * handle the acceleration of entity A
     * @param event the mouseEvent
     */
     @FXML
    void handleAccelerationA(MouseEvent event) {
        entityA.setAcceleration((double) accelerationASlider.getValue());
    }

    /**
     * handle the acceleration of entity B
     * @param event the mouseEvent
     */
    @FXML
    void handleAccelerationB(MouseEvent event) {
        entityB.setAcceleration((double) accelerationBSlider.getValue());
    }

    /**
     * handle the position of entity A
     * @param event 
     */
    @FXML
    void handlePositionA(MouseEvent event) {
        entityA.setPosition((double) positionASlider.getValue());
    }

    /**
     * handle the position of entity B
     * @param event 
     */
    @FXML
    void hundlePositionB(MouseEvent event) {
        entityB.setPosition((double) positionBSlider.getValue());
    }
}
