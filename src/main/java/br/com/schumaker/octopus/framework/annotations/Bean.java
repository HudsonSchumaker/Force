package br.com.schumaker.octopus.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Bean annotation is used to mark a method as a bean producer.
 * This annotation can be applied to methods to indicate that the method
 * produces a bean to be managed by the container.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Bean
 * public MyBean myBeanProducer() {
 *     return new MyBean();
 * }
 * }
 * </pre>
 *
 * @see Inject
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {}
