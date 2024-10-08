package br.com.schumaker.force.framework.ioc.annotations.controller;

import br.com.schumaker.force.framework.model.PatchHelper;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.view.ResponseView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.force.framework.web.http.Http.APPLICATION_JSON;

/**
 * The @Patch annotation is used to mark a method to handle HTTP HTTP_PATCH requests.
 * This annotation can be applied to methods in a controller class to indicate that the method
 * should be invoked to handle HTTP_PATCH requests to the specified URL.
 *
 * <p>
 * The value attribute specifies the URL pattern to which the method should respond.
 * The type attribute specifies the content type that the method consumes.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Patch(value = "/update/{id}", type = "application/json")
 * public ResponseView<Entity> updateEntity(@PathVariable Long id, @Payload Map<String, Object> patchMessage) {
 *     var oldEntity = entityRepository.findById(id);
 *     PatchHelper.applyPatch(oldEntity, patch);
 *     entityRepository.update(oldEntity);
 *     ...
 * }
 * }
 * </pre>
 *
 * @see Controller
 * @see Payload
 * @see PathVariable
 * @see PatchHelper
 * @see ResponseView
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Patch {
    String value() default "/";
    String type() default APPLICATION_JSON;
    int httpCode() default Http.HTTP_202;
}
