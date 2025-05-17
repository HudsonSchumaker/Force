package br.com.schumaker.force.framework.web.view;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.controller.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerOptionsTest {

    @Test
    public void testGetControllerOption() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyController.class));

        // Act
       var options = ControllerOptions.getAllowedMethods(MyController.class);

        // Assert
        assertNotNull(options);
        assertTrue(options.contains("GET"));
        assertTrue(options.contains("POST"));
    }

    @Test
    public void testControllerNotFound() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyController.class));

        // Act & Assert
        assertThrows(ForceException.class, ()-> ControllerOptions.getAllowedMethods(Controller.class));
    }

    // Test controller class
    @Controller
    public static class MyController {
        @Get("/users/{id}")
        public String getUserById(String id) {
            return "User: " + id;
        }

        @Post
        public String createUser(String user) {
            return "Created User: " + user;
        }

        @Options
        public String getOptions() {
            return "Options";
        }

        private void privateMethod() {
            // This method should not be accessible
        }
    }
}
