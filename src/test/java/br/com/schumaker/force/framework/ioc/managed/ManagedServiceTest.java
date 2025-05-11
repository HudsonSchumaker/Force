package br.com.schumaker.force.framework.ioc.managed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ManagedConfigurationTest class is responsible for testing the ManagedConfiguration class.
 * It includes tests for the builder method and the instance creation of managed configurations.
 *
 * @see ManagedConfiguration
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ManagedServiceTest {
    private ManagedService managedService;

    @BeforeEach
    void setUp() {
        managedService = ManagedService.builder(MyService.class);
    }

    @Test
    void testGetFqn() {
        assertEquals("br.com.schumaker.force.framework.ioc.managed.ManagedServiceTest$MyService", managedService.getFqn());
    }

    @Test
    void testGetInstance() {
        assertNotNull(managedService.getInstance());
        assertInstanceOf(MyService.class, managedService.getInstance());
    }

    // Test service class
    public static class MyService {
        public MyService() {}
    }
}
