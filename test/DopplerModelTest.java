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
         Entity observer = new Entity(10, 10, 10, 50);
         Entity source = new Entity(15, 20, 10, 40);
         double result = 41.238;
         
         DopplerModel model = new DopplerModel();
        //expected = model.updateObservedFrequency(observer, source);
         assertEquals(result, source.);
        
       }
       
       @Test 
       void updateKinematicState() {
           Entity entity = new Entity(10, 10, 10, 50);
           double time = 5;
           
           double result = 310;
           
           DopplerModel model = new DopplerModel();
           model.updateKinematicState(entity, time);
           
           assertEquals()
       }
       
       @Test
       public void update() {
           
       }
}
