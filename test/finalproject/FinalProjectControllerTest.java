/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package finalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jccpa
 */
public class FinalProjectControllerTest {
    
    public FinalProjectControllerTest() {
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

    /**
     * Test of initialize method, of class FinalProjectController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        FinalProjectController instance = new FinalProjectController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleReset method, of class FinalProjectController.
     */
    @Test
    public void testHandleReset() {
        System.out.println("handleReset");
        ActionEvent event = null;
        FinalProjectController instance = new FinalProjectController();
        instance.handleReset(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleExit method, of class FinalProjectController.
     */
    @Test
    public void testHandleExit() {
        System.out.println("handleExit");
        ActionEvent event = null;
        FinalProjectController instance = new FinalProjectController();
        instance.handleExit(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handlePause method, of class FinalProjectController.
     */
    @Test
    public void testHandlePause() {
        System.out.println("handlePause");
        ActionEvent event = null;
        FinalProjectController instance = new FinalProjectController();
        instance.handlePause(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleStart method, of class FinalProjectController.
     */
    @Test
    public void testHandleStart() {
        System.out.println("handleStart");
        ActionEvent event = null;
        FinalProjectController instance = new FinalProjectController();
        instance.handleStart(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
