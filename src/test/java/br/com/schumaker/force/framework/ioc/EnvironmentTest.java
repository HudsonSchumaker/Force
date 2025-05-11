package br.com.schumaker.force.framework.ioc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The EnvironmentTest class.
 * This class is responsible for testing the Environment class.
 *
 * @see Environment
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class EnvironmentTest {

    @Test
    public void testSetKey() {
        // Arrange
        Environment env = Environment.getInstance();
        env.setKey("app.name.label", "test");

        // Act
        String result = env.getKey("app.name.label");

        // Assert
        assertEquals("test", result);
    }

    @Test
    public void testGetKey() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        String result = env.getKey("force.server.port");

        // Assert
        assertEquals("8080", result);
    }

    @Test
    public void testGetServerPort() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        Integer result = env.getServerPort();

        // Assert
        assertEquals(8080, result);
    }

    @Test
    public void testGetServerContext() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        String result = env.getServerContext();

        // Assert
        assertEquals("/", result);
    }

    @Test
    public void testGetJwtExpiration() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        Long result = env.getJwtExpiration();

        // Assert
        assertEquals(7200, result);
    }

    @Test
    public void testGetServerMaxThreads() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        Integer result = env.getServerMaxThreads();

        // Assert
        assertTrue(result > 0);
    }

    @Test
    public void testGetServerQueueSize() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        Integer result = env.getServerQueueSize();

        // Assert
        assertTrue(result > 0);
    }
}
