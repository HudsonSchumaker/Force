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
 * @see ManagedController
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
    public void testGetMethodFound() {
        // Arrange & Act
        Triple<String, Method, List<Parameter>> result = managedController.getMethod("/users/123", "GET");

        // Assert
        assertNotNull(result);
        assertEquals("/users/{id}", result.first());
    }

    @Test
    public void testGetMethodNotFound() {
        // Arrange & Act & Assert
        assertThrows(RuntimeException.class, () -> {
            managedController.getMethod("/invalidRoute", "GET");
        });
    }

    @Test
    public void testPathMatches() {
        // Assert & Act
        assertTrue(managedController.pathMatches("/users/{id}", "/users/123"));
        assertFalse(managedController.pathMatches("/users/{id}", "/users"));
    }

    @Test
    public void testExtractPathVariables() {
        // Arrange & Act
        List<Object> pathVariables = managedController.extractPathVariables("/users/{id}", "/users/123");

        // Assert
        assertEquals(1, pathVariables.size());
        assertEquals("123", pathVariables.getFirst());
    }

    @Test
    public void testGetFqn() {
        // Arrange & Act
        String fqn = managedController.getFqn();

        // Assert
        assertEquals(MyController.class.getName(), fqn);
    }

    @Test
    public void testGetRoute() {
        // Arrange & Act
        String route = managedController.getRoute();

        // Assert
        assertEquals("/", route);
    }

    @Test
    public void getObjectInstance() {
        // Arrange & Act
        Object instance = managedController.getInstance();

        // Assert
        assertNotNull(instance);
        assertInstanceOf(MyController.class, instance);
    }

    // Test controller class
    @Controller
    public static class MyController {
        @Get("/users/{id}")
        public String getUserById(String id) {
            return "User: " + id;
        }

        private void privateMethod() {
            // This method should not be accessible
        }
    }
}
