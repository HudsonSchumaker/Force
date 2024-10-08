package br.com.schumaker.force.framework.ioc.managed;

import br.com.schumaker.force.framework.ioc.annotations.controller.Controller;
import br.com.schumaker.force.framework.ioc.annotations.controller.Get;
import br.com.schumaker.force.framework.model.Triple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ManagedControllerTest class.
 * This class is responsible for testing the ManagedController class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ManagedControllerTest {
    private ManagedController managedController;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        managedController = ManagedController.builder(MyController.class);
    }

    @Test
    void testGetMethod_Found() {
        // Arrange & Act
        Triple<String, Method, List<Parameter>> result = managedController.getMethod("/users/123", "GET");

        // Assert
        assertNotNull(result);
        assertEquals("/users/{id}", result.first());
    }

    @Test
    void testGetMethod_NotFound() {
        // Arrange & Act & Assert
        assertThrows(RuntimeException.class, () -> {
            managedController.getMethod("/invalidRoute", "GET");
        });
    }

    @Test
    void testPathMatches() {
        // Assert & Act
        assertTrue(managedController.pathMatches("/users/{id}", "/users/123"));
        assertFalse(managedController.pathMatches("/users/{id}", "/users"));
    }

    @Test
    void testExtractPathVariables() {
        // Arrange & Act
        List<Object> pathVariables = managedController.extractPathVariables("/users/{id}", "/users/123");

        // Assert
        assertEquals(1, pathVariables.size());
        assertEquals("123", pathVariables.getFirst());
    }

    // Test controller class
    @Controller
    public static class MyController {
        @Get("/users/{id}")
        public String getUserById(String id) {
            return "User: " + id;
        }
    }
}
