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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
 * @author Yasmine and Jacques
 */
public class FinalProjectController implements Initializable {
    LineChart<Number, Number> frequencyChartA;
    LineChart<Number, Number> frequencyChartB;
    LineChart<Number, Number> pressureChartA;
    LineChart<Number, Number> pressureChartB;
    
    @FXML
    private HBox graphHBox;
    @FXML
    private BorderPane root;
    @FXML
    private Pane truckA;
    @FXML
    private Pane truckB;
    @FXML
    private VBox entityPropertiesVBox;
    @FXML
    private TitledPane entityATitledPane;
    @FXML
    private TitledPane entityBTitledPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCharts();
        adjustPreferredSizes();
    }
    
    /**
     * Adjusts various UI element's sizing
     */
    private void adjustPreferredSizes() {
        VBox.setVgrow(entityPropertiesVBox, Priority.ALWAYS);
        
        VBox.setVgrow(graphHBox, Priority.ALWAYS);
        graphHBox.prefHeightProperty().bind(root.heightProperty().multiply(0.33));
    }
    
    /**
     * Initializes all relevant charts with chart title and axis titles
     */
    private void initializeCharts() {
        frequencyChartA = createChart("Frequency Observed by A", "Time (s)", "Frequency (Hz)");
        frequencyChartB = createChart("Frequency Observed by B", "Time (s)", "Frequency (Hz)");
        pressureChartA = createChart("Pressure Observed by A", "Time (s)", "Pressure (kPa)");
        pressureChartB = createChart("Pressure Observed by B", "Time (s)", "Pressure (kPa)");
        
        graphHBox.getChildren().addAll(frequencyChartA, frequencyChartB, pressureChartA, pressureChartB);
    }
    
    /**
     * Creates a chart with specified title and axis titles
     * @param title The title of the chart
     * @param xAxisLabel The x-axis label
     * @param yAxisLabel the y-axis label
     * @return The created chart
     */
    private LineChart<Number, Number> createChart(String title, String xAxisLabel, String yAxisLabel) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xAxisLabel);
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);
        
        LineChart<Number, Number> lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle(title);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);
        
        Series<Number, Number> series = new XYChart.Series<>();
        lineChart.getData().add(series);
        
        return lineChart;
    }
    
    /**
     * Adds a point P(x, y) to a chart
     * @param lineChart The chart to which the point should be added
     * @param x The x value of the point
     * @param y The y value of the point
     */
    private void addPoint(LineChart lineChart, double x, double y) {
        Series<Number, Number> series = (Series<Number, Number>) lineChart.getData().get(0);
        series.getData().add(new XYChart.Data<Number, Number>(x, y));
    }
    

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
