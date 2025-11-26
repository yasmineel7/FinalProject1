/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package finalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
import javafx.scene.shape.Rectangle;


/**
 * FXML Controller class
 *
 * @author Yasmine and Jacques
 */
public class FinalProjectController implements Initializable {
    DopplerModel model;
    
    LineChart<Number, Number> frequencyChartA;
    LineChart<Number, Number> frequencyChartB;
    LineChart<Number, Number> pressureChartA;
    LineChart<Number, Number> pressureChartB;
    
    @FXML private BorderPane root;
    @FXML private HBox graphHBox;
    @FXML private Pane truckA, truckB;
    @FXML private VBox entityPropertiesVBox;
    @FXML private Slider positionASlider, positionBSlider, velocityASlider, velocityBSlider, accelerationASlider, accelerationBSlider;
    @FXML private Pane scenePane;
    @FXML private Rectangle grass;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeModel();
        initializeCharts();
        initializeUIProperties();
        
        scenePane.setMouseTransparent(true);
        scenePane.widthProperty().addListener((obs, oldVal, newVal) -> {
            model.getEntityA().setMaxPosition(scenePane.getWidth() - truckA.getWidth());
            model.getEntityB().setMaxPosition(scenePane.getWidth() - truckB.getWidth());
            positionASlider.setMax(model.getEntityA().getMaxPosition());
            positionBSlider.setMax(model.getEntityB().getMaxPosition());
        });
        
        startSimulation();
        
    }
    
    private void initializeModel() {
        double speedOfSound = 343;
        Entity entityA = new Entity(0, 0, 0, 50);
        Entity entityB = new Entity(800, 0, 0, 100);
        
        model = new DopplerModel(speedOfSound, entityB, entityA, 0);
    }
    
    /**
     * Adjusts various UI element's sizing
     */
    private void initializeUIProperties() {
        grass.layoutYProperty().bind(scenePane.heightProperty().subtract(grass.getHeight()));
        grass.widthProperty().bind(scenePane.widthProperty());
        
        graphHBox.prefHeightProperty().bind(root.heightProperty().multiply(0.33));
        
        Platform.runLater(() -> { // runLater() since the trucks are panes with initial height 0 (they get calculated "later")
            truckA.layoutYProperty().bind(grass.layoutYProperty().subtract(truckA.getHeight()));
            truckB.layoutYProperty().bind(grass.layoutYProperty().subtract(truckB.getHeight()));
        });
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
     * Starts the simulation, updating the model and UI elements with respect to FPS
     */
    private void startSimulation() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = -1;
            
            @Override
            public void handle(long now) {
                if (lastTime < 0) {
                    lastTime = now;
                }
                
                double dt = (now - lastTime) / 1e9; // now and lastTime in nanoseconds, dt in seconds
                
                model.update(dt);
                
                addPoint(frequencyChartA, model.getTime(), model.getEntityA().getObservedFrequency());
                addPoint(frequencyChartB, model.getTime(), model.getEntityB().getObservedFrequency());
                
                updateTrucks();
                updateSliders();
                
                lastTime = now;
            }
        };
        
        timer.start();
    }
    
    /**
     * Updates the LayoutX of the trucks based on the positions of the model's entities
     */
    private void updateTrucks() {
        // Not using bind since that would technically violate MVC, but bind would be much better
        truckA.setLayoutX(model.getEntityA().getPosition());
        truckB.setLayoutX(model.getEntityB().getPosition());
    }
    
    /**
     * Updates the sliders based on the entity's kinematic properties
     */
    private void updateSliders() {
        // Not using bind since that would technically violate MVC, but bind would be much better
        Entity entityA = model.getEntityA();
        Entity entityB = model.getEntityB();
        
        if (!positionASlider.isValueChanging()) positionASlider.setValue(entityA.getPosition());
        if (!velocityASlider.isValueChanging()) velocityASlider.setValue(entityA.getVelocity());
        if (!accelerationASlider.isValueChanging()) accelerationASlider.setValue(entityA.getAcceleration());
        
        if (!positionBSlider.isValueChanging()) positionBSlider.setValue(entityB.getPosition());
        if (!velocityBSlider.isValueChanging()) velocityBSlider.setValue(entityB.getVelocity());
        if (!accelerationBSlider.isValueChanging()) accelerationBSlider.setValue(entityB.getAcceleration());
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
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(30);
        xAxis.setAutoRanging(false);
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        yAxis.setTickUnit(25);
        yAxis.setAutoRanging(false);
        
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
        series.getData().add(new XYChart.Data<>(x, y));
    }

    @FXML
    private void handleSlider(MouseEvent event) {
        Slider slider = (Slider) event.getSource();
        double value = slider.getValue();
        
        Entity entity;
        
        if (slider == positionASlider || slider == velocityASlider || slider == accelerationASlider) {
            entity = model.getEntityA();
        } else {
            entity = model.getEntityB();
        }
        
        if (slider == positionASlider || slider == positionBSlider) {
            entity.setPosition(value);
        } else if (slider == velocityASlider || slider == velocityBSlider) {
            entity.setVelocity(value);
        } else {
            entity.setAcceleration(value);
        }
    }
}
