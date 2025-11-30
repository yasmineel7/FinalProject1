/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package finalproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jccpa
 */
public class DopplerModelTest {
    private DopplerModel model;
    private Entity entityA;
    private Entity entityB;

    @BeforeEach
    void setUp() {
        entityA = new Entity(0, 10, 0, 100);
        entityB = new Entity(800, -5, 0, 50);
        model = new DopplerModel(343, entityB, entityA, 0);
    }

    @Test
    void testUpdate() {
        double oldTime = model.getTime();
        double oldPosA = entityA.getPosition();
        double oldPosB = entityB.getPosition();
        double oldVelocityA = entityA.getVelocity();
        double oldVelocityB = entityB.getVelocity();

        model.update(1.0);

        // Positions updated according to velocity
        assertEquals(oldPosA + oldVelocityA * 1.0, entityA.getPosition());
        assertEquals(oldPosB + oldVelocityB * 1.0, entityB.getPosition());

        // Velocities unchanged (acceleration = 0)
        assertEquals(oldVelocityA, entityA.getVelocity());
        assertEquals(oldVelocityB, entityB.getVelocity());

        // Time updated
        assertEquals(oldTime + 1.0, model.getTime());
    }
    
    @Test
    void testUpdateKinematicState() {
        // entityA at 0 moving left should stop
        entityA.setPosition(0);
        entityA.setVelocity(-10);
        model.update(1.0);
        assertEquals(0, entityA.getVelocity());
        assertEquals(0, entityA.getPosition());

        // entityB at max position moving right should stop
        entityB.setMaxPosition(1000);
        entityB.setPosition(1000);
        entityB.setVelocity(10);
        model.update(1.0);
        assertEquals(0, entityB.getVelocity());
        assertEquals(1000, entityB.getPosition());

        // Normal movement
        entityA.setPosition(500);
        entityA.setVelocity(5);
        entityA.setAcceleration(2);
        double expectedVelocity = 5 + 2 * 1.0;
        double expectedPosition = 500 + expectedVelocity * 1.0;
        model.update(1.0);
        assertEquals(expectedVelocity, entityA.getVelocity());
        assertEquals(expectedPosition, entityA.getPosition());
    }
    
    @Test
    void testUpdateObservedFrequency() {
        model.update(0);

        double observedA = entityA.getObservedFrequency();
        double observedB = entityB.getObservedFrequency();

        // Doppler effect: entityA should hear entityB as lower frequency (B moving away)
        assertTrue(observedA < entityB.getSourceFrequency());

        // entityB should hear entityA as higher frequency (A moving toward B)
        assertTrue(observedB > entityA.getSourceFrequency());
    }
}
