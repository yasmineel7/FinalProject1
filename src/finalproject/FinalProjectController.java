/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package finalproject;

import java.net.URL;
import java.util.ResourceBundle;
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
 * @author Yasmine
 */
public class FinalProjectController implements Initializable {
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 
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
