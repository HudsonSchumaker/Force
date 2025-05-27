package br.com.schumaker.force.framework.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Scheduled annotation is used to mark methods that should be executed periodically.
 * It allows specifying an initial delay before the first execution and a period for subsequent executions.
 * The time unit can also be specified.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Scheduled(initialDelay = 1000, period = 5000, unit = TimeUnit.MILLISECONDS)
 * public void scheduledTask() {
 *     // Task implementation
 * }
 * }
 * </pre>
 *
 * @see java.util.concurrent.TimeUnit
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {
    long initialDelay() default 0;
    long period() default 10;
    java.util.concurrent.TimeUnit unit() default java.util.concurrent.TimeUnit.SECONDS;

}
