package br.com.schumaker.force.framework.ioc.managed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagedConfigurationTest {
    private ManagedConfiguration managedConfiguration;

    @BeforeEach
    void setUp() {
        managedConfiguration = ManagedConfiguration.builder(MyConfiguration.class);
    }

    @Test
    void testGetFqn() {
        assertEquals("br.com.schumaker.force.framework.ioc.managed.ManagedConfigurationTest$MyConfiguration", managedConfiguration.getFqn());
    }

    @Test
    void testGetInstance() {
        assertNotNull(managedConfiguration.getInstance());
        assertInstanceOf(MyConfiguration.class, managedConfiguration.getInstance());
    }

    // Test configuration class
    public static class MyConfiguration {}
}