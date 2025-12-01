/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package finalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jacques
 */
public class FinalProjectControllerTest {
    // We tried everything we could, but Junit is not working for us.
    // We wrote some code that gives the main idea behind Junit testing,
    // but we are unable to actually test it out and make sure that it works.
    
    // Since none of this works, we pretended that everything is public to make
    // making writing the fake code easier
    FinalProjectController controller;

    @Test
    void testModelInitialization() {
        assertNotNull(controller.model);
        assertEquals(343, controller.model.getVelocityWave());
        assertEquals(0, controller.model.getEntityA().getPosition());
        assertEquals(800, controller.model.getEntityB().getPosition());
    }

    @Test
    void testChartsAreCreated() {
        assertNotNull(controller.frequencyChartA);
        assertNotNull(controller.frequencyChartB);
        assertNotNull(controller.waveLengthChartA);
        assertNotNull(controller.waveLengthChartB);

        assertEquals(1, controller.frequencyChartA.getData().size());
        assertEquals(1, controller.frequencyChartB.getData().size());
    }

    @Test
    void testGetTruckFromEntity() {
        assertSame(controller.truckA, controller.getTruckFromEntity(controller.model.getEntityA()));
        assertSame(controller.truckB, controller.getTruckFromEntity(controller.model.getEntityB()));
    }

    @Test
    void testGetEntityFromTruck() {
        assertSame(controller.model.getEntityA(), controller.getEntityFromTruck(controller.truckA));
        assertSame(controller.model.getEntityB(), controller.getEntityFromTruck(controller.truckB));
    }

    @Test
    void testTruckUpdatesPosition() {
        controller.model.getEntityA().setPosition(123.0);
        controller.updateTruck(controller.truckA);
        assertEquals(123.0, controller.truckA.getLayoutX());
    }

    @Test
    void testTruckFacesLeftWhenVelocityNegative() {
        controller.model.getEntityA().setVelocity(-10);
        controller.updateTruck(controller.truckA);

        assertEquals(-1, controller.truckA.getScaleX());
    }

    @Test
    void testTruckFacesRightWhenVelocityPositive() {
        controller.model.getEntityA().setVelocity(10);
        controller.updateTruck(controller.truckA);

        assertEquals(1, controller.truckA.getScaleX());
    }

    @Test
    void testTruckColorUpdates() {
        controller.truckAColor = Color.RED;
        controller.wheelsAColor = Color.BLUE;
        controller.updateTruckColors();

        assertEquals(Color.RED, controller.truckABody.getFill());
        assertEquals(Color.BLUE, controller.truckAWheel1.getFill());
    }

    @Test
    void testResetColors() {
        controller.truckAColor = Color.BLACK;
        controller.wheelsAColor = Color.YELLOW;

        controller.resetColorsToDefault();

        assertEquals(Color.WHITE, controller.truckAColor);
        assertEquals(Color.WHITE, controller.wheelsAColor);
    }

    @Test
    void testAddPointToChart() {
        LineChart<Number, Number> chart = controller.frequencyChartA;
        controller.addPoint(chart, 1.0, 100.0);

        assertEquals(1, chart.getData().get(0).getData().size());
        assertEquals(1.0, chart.getData().get(0).getData().get(0).getXValue());
        assertEquals(100.0, chart.getData().get(0).getData().get(0).getYValue());
    }

    @Test
    void testClearCharts() {
        controller.addPoint(controller.frequencyChartA, 1, 1);
        controller.addPoint(controller.waveLengthChartB, 2, 5);

        controller.clearAllCharts();

        assertEquals(0, controller.frequencyChartA.getData().get(0).getData().size());
        assertEquals(0, controller.waveLengthChartB.getData().get(0).getData().size());
    }

    @Test
    void testCreateSoundWaveAddsCircle() {
        int before = controller.scenePane.getChildren().size();

        controller.createSoundWave(controller.model.getEntityA(), Color.RED);

        int after = controller.scenePane.getChildren().size();

        assertTrue(after > before);
    }

    @Test
    void testSoundWaveTransitionAdded() {
        controller.createSoundWave(controller.model.getEntityA(), Color.RED);

        assertFalse(controller.soundWaveTransitions.isEmpty());
    }

    @Test
    void testResetRestoresModelValues() {
        controller.model.getEntityA().setPosition(123);
        controller.model.setTime(4.5);

        controller.handleReset(null);

        assertEquals(0, controller.model.getEntityA().getPosition());
        assertEquals(800, controller.model.getEntityB().getPosition());
        assertEquals(0, controller.model.getTime());
    }
}
