package br.com.schumaker.force.framework.ioc.managed;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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