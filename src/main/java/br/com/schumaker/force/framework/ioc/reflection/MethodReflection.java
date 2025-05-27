package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.ioc.annotations.Scheduled;
import br.com.schumaker.force.framework.ioc.annotations.bean.Bean;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.managed.ManagedBean;
import br.com.schumaker.force.framework.scheduler.Scheduler;

import java.lang.reflect.Modifier;

/**
 * The MethodReflection class provides utility methods for handling methods annotated with @Bean.
 * It uses reflection to instantiate beans and register them with the IoC container.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.1.0
 */
public final class MethodReflection {
    private static final MethodReflection INSTANCE = new MethodReflection();
    private final Scheduler scheduler = Scheduler.getInstance();

    private MethodReflection() {}

    public static MethodReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Instantiates beans for methods annotated with @Bean in the specified instance and registers them with the IoC container.
     *
     * @param instance the instance whose methods are to be processed.
     * @throws ForceException if an error occurs during bean instantiation.
     */
    public void instantiateBean(Object instance) {
        try {
            var methods = instance.getClass().getDeclaredMethods();
            for (var method : methods) {
                if (method.isAnnotationPresent(Bean.class)) {
                    Object bean = method.invoke(instance);
                    var managedBean = ManagedBean.builder(bean.getClass(), bean);
                    IoCContainer.getInstance().registerBean(managedBean);
                }
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage());
        }
    }

    public void scheduleTask(Object instance) {
        try {
            var methods = instance.getClass().getDeclaredMethods();
            for (var method : methods) {
                if (Modifier.isPublic(method.getModifiers())) {
                    if (method.isAnnotationPresent(Scheduled.class)) {
                        var annotation = method.getAnnotation(Scheduled.class);
                        var initialDelay = annotation.initialDelay();
                        var period = annotation.period();
                        var unit = annotation.unit();

                        if (initialDelay < 0 || period <= 0) {
                            throw new ForceException("Invalid initial delay or period for scheduled method: " + method.getName());
                        }

                        Runnable task = () -> {
                            try {
                                method.invoke(instance);
                            } catch (Exception ex) {
                                throw new ForceException(ex.getMessage());
                            }
                        };

                        if (initialDelay > 0) {
                            scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
                        } else {
                            scheduler.schedule(task, period, unit);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage());
        }
    }
}
