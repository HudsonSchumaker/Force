package br.com.schumaker.force.framework.ioc.annotations.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Payload annotation is used to mark a method parameter as the payload of an HTTP request.
 * This annotation can be applied to parameters to indicate that the parameter should be
 * bound to the body of the HTTP request.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Controller("/api")
 * public class MyController {
 *
 *     @Post(value = "/submit", type = "application/json")
 *     public ResponseView<ProductView> submitData(@Payload Form form) {
 *         // Handle HTTP_POST request
 *         return ResponseView.of(new ProductView(), Http.HTTP_201);
 *     }
 * }
 * }
 * </pre>
 *
 * @see Controller
 * @see Post
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Payload {}
