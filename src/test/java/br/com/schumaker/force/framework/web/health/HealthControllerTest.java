package br.com.schumaker.force.framework.web.health;

import br.com.schumaker.force.framework.web.view.ResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The HealthControllerTest class.
 * This class is responsible for testing the HealthController class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class HealthControllerTest {
    private HealthController healthController;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        // Arrange
        HealthService healthService = new HealthService();
        healthController = new HealthController(healthService);
    }

    @Test
    public void testHealth() {
         // Act
         ResponseView<String> response = healthController.health();

         // Assert
         assertEquals("UP", response.getBody());
         assertEquals(200, response.getHttpCode());
    }

    @Test
    public void testHealthInfo() {
         // Act
         ResponseView<HealthInfoDTO> response = healthController.healthInfo();

         // Assert
         assertEquals(200, response.getHttpCode());
         assertNotNull(response.getBody().hostName());
         assertNotNull(response.getBody().ip());
         assertNotNull(response.getBody().forceVersion());
         assertNotNull(response.getBody().osName());
         assertNotNull(response.getBody().osVersion());
         assertNotNull(response.getBody().osArch());
         assertNotNull(response.getBody().jvmName());
         assertNotNull(response.getBody().javaVendor());
         assertNotNull(response.getBody().javaVersion());
         assertNotNull(response.getBody().jvmUsedMemory());
         assertNotNull(response.getBody().jvmUsedMemory());
         assertNotNull(response.getBody().jvmUsedMemory());
    }
}
