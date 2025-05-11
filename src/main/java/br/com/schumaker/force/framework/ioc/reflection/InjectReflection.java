package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.ioc.annotations.bean.Inject;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
/**
 * The InjectReflection class provides utility methods for injecting dependencies into fields annotated with @Inject.
 * It uses reflection to set the field values of an instance with the corresponding beans from the IoC container.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.1.0
 */
public final class InjectReflection {
    private static final InjectReflection INSTANCE = new InjectReflection();
    private static final IoCContainer iocContainer = IoCContainer.getInstance();

    private InjectReflection() {}

    public static InjectReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Injects the field values of the specified instance with the corresponding beans from the IoC container.
     * Fields annotated with @Inject are set with the appropriate bean instances.
     * If the field type is an interface, the container will inject all compatible beans.
     *
     * @param instance the instance whose fields are to be injected.
     * @throws ForceException if an error occurs during field injection.
     */
    public void injectFieldBean(Object instance) {
        try {
            var fields = instance.getClass().getDeclaredFields();
            for (var field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    var type = field.getType();
                    var genericType = field.getGenericType();

                    if (genericType instanceof ParameterizedType) {
                        // Does not support multiple generic types ex. Map<K, V>, only List<T> is supported
                        var actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

                        // Check if the actual type is an interface
                        if (actualType instanceof Class && ((Class<?>) actualType).isInterface()) {
                            var components = iocContainer.getComponents();
                            var compatibleComponentList = new ArrayList<>(); // List of compatible components that will be injected
                            for (var component : components) {
                                var componentClass = component.getInstance().getClass();
                                // Check if the component is assignable to the actual type (interface)
                                if (((Class<?>) actualType).isAssignableFrom(componentClass)) {
                                    compatibleComponentList.add(component.getInstance());
                                }
                            }
                            field.set(instance, compatibleComponentList);
                        }
                    } else {
                        var value = IoCContainer.getInstance().getBean(type.getName());
                        if (value != null && value.getInstance() != null) {
                            field.set(instance, value.getInstance());
                        }
                    }
                }
                field.setAccessible(false);
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage());
        }
    }
}
