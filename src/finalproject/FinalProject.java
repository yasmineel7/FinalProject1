/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package finalproject;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Yasmine
 */
public class FinalProject extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("finalProject.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(1200);
        stage.setMinHeight(800);
        stage.setTitle("Doppler Effect Simulation");
        
        stage.show();
        }
        catch (IOException e) {
            
        }
    }
    
}
