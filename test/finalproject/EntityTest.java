/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package finalproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jacques
 */
public class EntityTest {
    
    @Test
    void testSetPosition() {
        Entity entity = new Entity();
        entity.setMaxPosition(100);

        entity.setPosition(150);
        assertEquals(100, entity.getPosition());

        entity.setPosition(-10);
        assertEquals(0, entity.getPosition());

        entity.setPosition(50);
        assertEquals(50, entity.getPosition());
    }
}
