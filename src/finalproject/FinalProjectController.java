/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package finalproject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author Yasmine and Jacques
 */
public class FinalProjectController implements Initializable {
    private DopplerModel model;
    
    private LineChart<Number, Number> frequencyChartA;
    private LineChart<Number, Number> frequencyChartB;
    private LineChart<Number, Number> waveLengthChartA;
    private LineChart<Number, Number> waveLengthChartB;
    
    private AnimationTimer timer;
    private boolean paused = false;
    
    private ArrayList<ParallelTransition> soundWaveTransitions = new ArrayList<>();

    @FXML private BorderPane root;
    @FXML private HBox graphHBox;
    @FXML private Pane truckA, truckB;
    @FXML private Slider positionASlider, positionBSlider, velocityASlider, velocityBSlider, accelerationASlider, accelerationBSlider;
    @FXML private Pane scenePane;
    @FXML private Rectangle grass;
    @FXML private Button startButton, pauseButton, exitButton, resetButton;
    @FXML private Rectangle truckABody, truckACabin, truckBBody, truckBCabin;
    @FXML private Circle truckAWheel1, truckAWheel2, truckBWheel1, truckBWheel2;
    
    // Current colors
    private Color truckAColor = Color.WHITE;
    private Color truckBColor = Color.WHITE;
    private Color wheelsAColor = Color.WHITE;
    private Color wheelsBColor = Color.WHITE;
    private Color grassColor = Color.web("#018a07");
    
     // Settings window components and speed of animation
    private Stage settingsStage;
    private double animationSpeed = 1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeModel();
        initializeCharts();
        initializeUIProperties();
        initializeScenePaneSizing();
        
        startSimulation();
    }
    
    /**
     * Initializes the model and its required parameters
     */
    private void initializeModel() {
        double speedOfSound = 343;
        Entity entityA = new Entity(0, 0, 0, 50);
        Entity entityB = new Entity(800, 0, 0, 100);
        
        model = new DopplerModel(speedOfSound, entityB, entityA, 0);
    }
    
    /**
     * Adjusts various UI element's sizing and bindings
     */
    private void initializeUIProperties() {
        grass.layoutYProperty().bind(scenePane.heightProperty().subtract(grass.getHeight()));
        grass.widthProperty().bind(scenePane.widthProperty());
        
        graphHBox.prefHeightProperty().bind(root.heightProperty().multiply(0.33));
        
        Platform.runLater(() -> { // runLater() since the trucks are panes with initial height 0 (they get calculated "later")
            truckA.layoutYProperty().bind(grass.layoutYProperty().subtract(truckA.getHeight()));
            truckB.layoutYProperty().bind(grass.layoutYProperty().subtract(truckB.getHeight()));
        });
        
        // Clip prevents soundwaves from going outside of scenePane
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(scenePane.widthProperty());
        clip.heightProperty().bind(scenePane.heightProperty());
        scenePane.setClip(clip);
    }
    
    /**
     * Initializes all relevant charts with chart title and axis titles
     */
    private void initializeCharts() {
        frequencyChartA = createChart("Frequency Observed by A", "Time (s)", "Frequency (Hz)");
        frequencyChartB = createChart("Frequency Observed by B", "Time (s)", "Frequency (Hz)");
        waveLengthChartA = createChart("Wavelength Observed by A", "Time (s)", "Wavelength (cm)");
        waveLengthChartB = createChart("Wavelength Observed by B", "Time (s)", "Wavelength (cm)");
        
        graphHBox.getChildren().addAll(frequencyChartA, frequencyChartB, waveLengthChartA, waveLengthChartB);
    }
    
    /**
     * Changes the max position of the entities and sliders based on the scenePane width
     */
    private void initializeScenePaneSizing() {
        scenePane.widthProperty().addListener((obs, oldVal, newVal) -> {
            model.getEntityA().setMaxPosition(scenePane.getWidth() - truckA.getWidth());
            model.getEntityB().setMaxPosition(scenePane.getWidth() - truckB.getWidth());
            positionASlider.setMax(model.getEntityA().getMaxPosition());
            positionBSlider.setMax(model.getEntityB().getMaxPosition());
        });
    }
        
    /**
     * Starts the simulation, updating the model and UI elements with respect to FPS
     */
    private void startSimulation() {
         timer = new AnimationTimer() {
            private long lastTime = -1;
            
            private double timeSinceLastSoundWave = 0;
            private double soundWaveInterval = 0.3;
            
            @Override
            public void handle(long now) { // Called every frame when timer is running
                if (lastTime < 0 || paused) { // lastTime initializes to now since otherwise graphs wouldn't start at 0
                    lastTime = now;
                    paused = false;
                }
                
                double dt = (now - lastTime) / 1e9; // now and lastTime in nanoseconds, dt in seconds
                
                timeSinceLastSoundWave += dt;
                if (timeSinceLastSoundWave > soundWaveInterval) {
                    timeSinceLastSoundWave = 0;
                    
                    createSoundWave(model.getEntityA(), Color.RED);
                    createSoundWave(model.getEntityB(), Color.BLUE);
                }
                
                model.update(dt);
                
                addPoint(frequencyChartA, model.getTime(), model.getEntityA().getObservedFrequency());
                addPoint(frequencyChartB, model.getTime(), model.getEntityB().getObservedFrequency());
                addPoint(waveLengthChartA, model.getTime(), model.getVelocityWave() / model.getEntityA().getObservedFrequency() * 10); // Multiplied by 10 for centimeters
                addPoint(waveLengthChartB, model.getTime(), model.getVelocityWave() / model.getEntityB().getObservedFrequency() * 10);
                
                updateChartBounds(frequencyChartA);
                updateChartBounds(frequencyChartB);
                updateChartBounds(waveLengthChartA);
                updateChartBounds(waveLengthChartB);
                
                updateTruck(truckA);
                updateTruck(truckB);
                
                updateSliders();
                
                lastTime = now;
            }
        };
        
         timer.start();
    }
    
    /**
     * update the chart to only see a part of the graph
     * @param lineChart the lineChart used
     */
    private void updateChartBounds(LineChart lineChart) {
        Series<Number, Number> series = (Series<Number, Number>) lineChart.getData().get(0);
         NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        
        double latestX = (double) series.getData().getLast().getXValue();
        double windowSize = 10;
        
        xAxis.setUpperBound(latestX);
        xAxis.setLowerBound(Math.max(windowSize, latestX) - windowSize);   
    }
    
    /**
     * Creates an expanding and fading sound wave based on the position of the
     * originator of sound
     * @param originator The originator (source) of the sound
     * @param color The color of the sound wave
     */
    private void createSoundWave(Entity originator, Color color) {
        Pane truck = getTruckFromEntity(originator);
        
        double centerX = originator.getPosition() + truck.getWidth() / 2;
        double centerY = truck.getLayoutY() + truck.getHeight() / 2;
        
        double baseAnimationLength = 1;
        double actualAnimationLength = baseAnimationLength / animationSpeed;
        
        Circle soundWave = new Circle(centerX, centerY, 0, Color.TRANSPARENT);
        soundWave.setStroke(color);
        soundWave.setStrokeWidth(6);
        
        FadeTransition fade = new FadeTransition(Duration.seconds(actualAnimationLength), soundWave);
        fade.setFromValue(1);
        fade.setToValue(0);
        
        Timeline size = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(soundWave.radiusProperty(), 0)),
                new KeyFrame(Duration.seconds(actualAnimationLength), new KeyValue(soundWave.radiusProperty(), actualAnimationLength * model.getVelocityWave()))
        );
        
        ParallelTransition pt = new ParallelTransition(fade, size);
        pt.setOnFinished(e -> {
            soundWaveTransitions.remove(pt);
            scenePane.getChildren().remove(soundWave);
        });
        pt.play();
        
        scenePane.getChildren().add(soundWave);
        soundWaveTransitions.add(pt);
    }
    
    /**
     * Returns the truck associated with the given entity
     * @param entity The entity to find the truck from
     * @return The truck associated with the entity
     */
    private Pane getTruckFromEntity(Entity entity) {
        if (entity == model.getEntityA()) {
            return truckA;
        }
        return truckB;
    }
    
    /**
     * Returns the entity associated with the given truck
     * @param truck The truck to find the entity from
     * @return The entity associated with the truck
     */
    private Entity getEntityFromTruck(Pane truck) {
        if (truck == truckA) {
            return model.getEntityA();
        }
        return model.getEntityB();
    }
    
    /**
     * Updates the LayoutX of the trucks based on the positions of the model's entities
     * and the orientation of the trucks based on their direction of movement
     */
    private void updateTruck(Pane truck) {
        Entity entity = getEntityFromTruck(truck);

        truck.setLayoutX(entity.getPosition());

        if (entity.getVelocity() < 0) {
            truck.setScaleX(-1);
            truck.getChildren().getLast().setScaleX(-1);
        } else if (entity.getVelocity() > 0) { // Not using else keyword to avoid flip flopping between left and right in some cases
            truck.setScaleX(1);
            truck.getChildren().getLast().setScaleX(1);
        }
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
        xAxis.setAnimated(false);
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(300);
        yAxis.setTickUnit(25);
        yAxis.setAutoRanging(false);
        yAxis.setAnimated(false);
        
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
    
    /**
     * Clears the points from all charts
     */
    private void clearAllCharts() {
        for (LineChart<Number, Number> chart : new LineChart[]{frequencyChartA, frequencyChartB, waveLengthChartA, waveLengthChartB}) {
            if (chart != null && chart.getData().size() > 0) {
            Series<Number, Number> series = (Series<Number, Number>) chart.getData().get(0);
            series.getData().clear();
            }
        }
    }

    /**
     * handle the value selected by the user on the sliders
     * @param event the mouse event
     */
    @FXML
    private void handleKinematicSliders(MouseEvent event) {
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
    
    /**
     * create the window to change the appearance of the trucks
     */
    private void createSettingsWindow() {
        if (settingsStage != null) {
            settingsStage.show();
            settingsStage.toFront();
            return;
        }
        
        settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        
        //colour of truckA and truckB
         Label truckALabel = new Label("Truck A Color:");
        ColorPicker truckAColorPicker = new ColorPicker(truckAColor);
        truckAColorPicker.setOnAction(e -> {
            truckAColor = truckAColorPicker.getValue();
            updateTruckColors();
        });
        
        Label truckBLabel = new Label("Truck B Color:");
        ColorPicker truckBColorPicker = new ColorPicker(truckBColor);
        truckBColorPicker.setOnAction(e -> {
            truckBColor = truckBColorPicker.getValue();
            updateTruckColors();
        });
        
        //Wheels of TruckA and TruckB
        Label wheelsALabel = new Label("Wheels Color of TruckA:");
        ColorPicker wheelsAColorPicker = new ColorPicker(wheelsAColor);
        wheelsAColorPicker.setOnAction(e -> {
            wheelsAColor = wheelsAColorPicker.getValue();
            updateTruckColors();
        });
        
        Label wheelsBLabel = new Label("Wheels Color of TruckB:");
        ColorPicker wheelsBColorPicker = new ColorPicker(wheelsBColor);
        wheelsBColorPicker.setOnAction(e -> {
            wheelsBColor = wheelsBColorPicker.getValue();
            updateTruckColors();
        });
        
        //button exitSettings
        Button exitSettingsButton = new Button("Exit Settings");
        exitSettingsButton.setOnAction(e -> {
            Stage stage = (Stage) exitSettingsButton.getScene().getWindow();
            stage.close();
        });
        
        //animation
        Label speedLabel = new Label("Speed: " + String.format("%.1fx", animationSpeed));
        Slider speedSlider = new Slider(0.25, 3.0, animationSpeed);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(0.5);
        speedSlider.setBlockIncrement(0.1);
    
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
        animationSpeed = newVal.doubleValue();
        speedLabel.setText("Speed: " + String.format("%.1fx", animationSpeed));
            });
    
        // Add description labels and Hbox
        Label slowLabel = new Label("Slow");
        Label fastLabel = new Label("Fast");
        HBox speedLabels = new HBox(10);
        speedLabels.getChildren().addAll(slowLabel, fastLabel);
        
        //buttonn reset
        Button resetButton = new Button("Reset to Defaults");
        resetButton.setOnAction(e -> {
            resetColorsToDefault();
            truckAColorPicker.setValue(truckAColor);
            truckBColorPicker.setValue(truckBColor);
            wheelsAColorPicker.setValue(wheelsAColor);
            wheelsBColorPicker.setValue(wheelsBColor);
        });
        
        root.getChildren().addAll(truckALabel, truckAColorPicker, truckBLabel, truckBColorPicker, 
                wheelsALabel, wheelsAColorPicker, wheelsBLabel, wheelsBColorPicker
                ,speedLabel, speedSlider,speedLabels,resetButton, exitSettingsButton);
        
        Scene scene = new Scene(root, 400, 500);
        settingsStage.setScene(scene);
        settingsStage.show();
    }
    
    /**
     * update the color of trucks
     */
    private void updateTruckColors() {
        //change color of TruckA 
         if (truckABody != null) truckABody.setFill(truckAColor);
        if (truckACabin != null) truckACabin.setFill(truckAColor);
        if (truckAWheel1 != null) truckAWheel1.setFill(wheelsAColor);
        if (truckAWheel2 != null) truckAWheel2.setFill(wheelsAColor);
        
        //change color of TruckB
         if (truckBBody != null) truckBBody.setFill(truckBColor);
        if (truckBCabin != null) truckBCabin.setFill(truckBColor);
        if (truckBWheel1 != null) truckBWheel1.setFill(wheelsBColor);
        if (truckBWheel2 != null) truckBWheel2.setFill(wheelsBColor);
        
    }
    
    /**
     * the reset the color of the Trucks to their original one
     */
    private void resetColorsToDefault() {
        truckAColor = Color.WHITE;
        truckBColor = Color.WHITE;
        wheelsAColor = Color.WHITE;
        wheelsBColor = Color.WHITE;
        grassColor = Color.web("#018a07");
        
        updateTruckColors();
    }
    
    /**
     * handle the settings of the menuItem settings
     * @param event the action event
     */
    @FXML
    void handleSettings(ActionEvent event) {
        createSettingsWindow();
    }
    
    /**
     * handle the button reset
     * @param event the action event
     */
    @FXML
    void handleReset(ActionEvent event) {
        
        //set the positions
        model.getEntityA().setPosition(0);
        model.getEntityB().setPosition(800);
        
        //set the accelerations
        model.getEntityA().setAcceleration(0);
        model.getEntityB().setAcceleration(0);
        
        //set the velocities
        model.getEntityA().setVelocity(0);
        model.getEntityB().setVelocity(0);
        
        //set the time
        model.setTime(0);
        
        for (ParallelTransition pt : soundWaveTransitions) {
            pt.stop();
            scenePane.getChildren().removeIf(node -> node instanceof Circle);
        }
        
        //clear charts
        clearAllCharts();
        //reset the colors of the trucks and animation
        resetColorsToDefault();
        animationSpeed = 1;
    }
    
    /**
     * handle the exit button
     * @param event the action event
     */
      @FXML
    void handleExit(ActionEvent event) {
        timer.stop();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * handle the pause button
     * @param event the action event
     */
    @FXML
    void handlePause(ActionEvent event) {
        timer.stop();

        for (ParallelTransition pt : soundWaveTransitions) {
            pt.pause();
        }

        startButton.setText("Unpause");
        paused = true;
    }
    
    /**
     * handle the start method
     * @param event the action event 
     */
    @FXML
    void handleStart(ActionEvent event) {
        timer.start();
        
        for (ParallelTransition pt : soundWaveTransitions) {
            pt.play();
        }
        
        startButton.setText("Play");
    }
}
