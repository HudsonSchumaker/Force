package br.com.schumaker.force.framework.ioc.managed;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The ManagedComponentTest class is responsible for testing the ManagedComponent class.
 * It includes tests for the builder method and the instance creation of managed components.
 *
 * @see ManagedComponent
 * @author Hudson Schumaker
 * @version 1.0.0
 */
class ManagedComponentTest {

    @Test
    void testBuilder() {
        Class<?> componentClass = String.class; // Example component class
        ManagedComponent managedComponent = ManagedComponent.builder(componentClass);

        assertNotNull(managedComponent);
        assertEquals(componentClass.getName(), managedComponent.getFqn());
        assertNotNull(managedComponent.getInstance());
        assertInstanceOf(String.class, managedComponent.getInstance());
    }
}