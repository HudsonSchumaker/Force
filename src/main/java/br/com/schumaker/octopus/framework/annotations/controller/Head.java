package br.com.schumaker.octopus.framework.annotations.controller;

import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.web.http.Http.APPLICATION_JSON;

/**
 * Annotation to map HTTP HEAD requests onto specific handler methods.
 *
 * <p>This annotation can be used on methods to define them as handlers for HTTP HEAD requests.
 * The default path is "/", the default content type is "application/json", and the default HTTP response code is 200.</p>
 *
 * <pre>
 * &#64;Head(value = "/example", type = "application/json", httpCode = 200)
 * public void handleHeadRequest() {
 *     // handler code
 * }
 * </pre>
 *
 * @see Http
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Head {
    String value() default "/";
    String type() default APPLICATION_JSON;
    int httpCode() default Http.HTTP_200;
}
