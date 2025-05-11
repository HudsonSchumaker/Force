package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.ioc.annotations.bean.Inject;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.managed.ManagedBean;
import br.com.schumaker.force.framework.ioc.managed.ManagedComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InjectReflectionTest {
    private InjectReflection injectReflection;

    @BeforeEach
    public void setUp() {
        injectReflection = InjectReflection.getInstance();
    }

    @Test
    public void testInjectFieldBean() {
        // Arrange
        TestClass testInstance = new TestClass();
        IoCContainer.getInstance().registerBean(ManagedBean.builder(TestDependency.class, new TestDependency()));

        // Act
        injectReflection.injectFieldBean(testInstance);

        // Assert
        assertNotNull(testInstance.getDependency());
    }

    static class TestClass {
        @Inject
        private TestDependency dependency;

        public TestDependency getDependency() {
            return dependency;
        }
    }

    @Test
    public void testInjectFieldBeanWithList() {
        // Arrange
        TestClassWithList testInstance = new TestClassWithList();
        IoCContainer.getInstance().registerComponent(List.of(TestDependency.class));
        IoCContainer.getInstance().registerBean(ManagedBean.builder(TestClassWithList.class, new TestClassWithList()));

        // Act
        injectReflection.injectFieldBean(testInstance);

        // Assert
        assertNotNull(testInstance.getDependencies());
        assertFalse(testInstance.getDependencies().isEmpty());
    }

    interface Validator {
        void validate();
    }

    static class TestDependency implements Validator{
        @Override
        public void validate() {
            // Validation logic
        }
    }
    static class TestClassWithList {
        @Inject
        private List<Validator> dependencies;

        public List<Validator> getDependencies() {
            return dependencies;
        }
    }
}