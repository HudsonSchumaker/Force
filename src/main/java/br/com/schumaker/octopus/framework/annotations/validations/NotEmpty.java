package br.com.schumaker.octopus.framework.annotations.validations;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.ioc.reflection.validation.NotEmptyValidation.NOT_EMPTY_VALIDATION_MESSAGE;

/**
 * The @NotEmpty annotation is used to mark a field as a not empty value.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as a not empty value.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @NotEmpty
 *     private String name;
 *     // Getters and Setters
 * }
 * }
 * </pre>
 *
 * @see Max
 * @see Min
 * @see Past
 * @see Range
 * @see Email
 * @see Regex
 * @see Future
 * @see NotNull
 * @see NotBlank
 * @see Validate
 * @see Payload
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {
    String value() default NOT_EMPTY_VALIDATION_MESSAGE;
}
