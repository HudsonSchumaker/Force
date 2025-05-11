package br.com.schumaker.force.framework.ioc.reflection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The ClassReflectionTest class is responsible for testing the ClassReflection class.
 * It includes tests for the instantiate method and handles field injection and value annotations.
 *
 * @see ClassReflection
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ClassReflectionTest {
    private ClassReflection classReflection;

    @BeforeEach
    public void setUp() {
        classReflection = ClassReflection.getInstance();
    }

    @Test
    public void testInstantiate() {
        // Act
        Object instance = classReflection.instantiate(TestClass.class);

        // Assert
        assertNotNull(instance);
    }

    static class ParameterClass {
        // This class is used for testing purposes
    }

    static class TestClass {
        private ParameterClass parameterClass;

        public TestClass(ParameterClass parameterClass) {
            this.parameterClass = parameterClass;
        }
    }
}
