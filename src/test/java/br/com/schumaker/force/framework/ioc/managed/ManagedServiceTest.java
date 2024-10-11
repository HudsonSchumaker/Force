package br.com.schumaker.force.framework.ioc.managed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ManagedServiceTest class.
 * This class is responsible for testing the ManagedService class.
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
