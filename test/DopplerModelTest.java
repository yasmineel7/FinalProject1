/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import finalproject.DopplerModel;
import finalproject.Entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 6298686
 */
public class DopplerModelTest {
    private DopplerModel dopplerModel;
    private Entity entityA;
    private Entity entityB;
    
    public DopplerModelTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        entityA = new Entity(0.0, 10.0, 0.0, 440.0); 
        entityB = new Entity(100.0, -5.0, 0.0, 0.0);
        dopplerModel = new DopplerModel(343.0, entityB, entityA, 0.0);
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
   
       @Test
        void updateObservedFrequency() {
          dopplerModel.update(0.0);
          
        double observedFreqB = entityB.getObservedFrequency();
        assertNotEquals(0.0, observedFreqB);
        assertTrue(observedFreqB > 0);
       }
       
       @Test 
       void updateKinematicState() {
          
        double initialPositionA = entityA.getPosition();
        double initialVelocityA = entityA.getVelocity();
        double dt = 1.0;
        
        dopplerModel.update(dt);
        
        // Position should increase due to positive velocity
        assertTrue(entityA.getPosition() > initialPositionA);
        // Velocity should remain the same since acceleration is 0
        assertEquals(initialVelocityA, entityA.getVelocity());
       }
       
       @Test
       public void update() {
           
       }
}
