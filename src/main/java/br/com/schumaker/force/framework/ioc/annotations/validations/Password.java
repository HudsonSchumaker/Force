package br.com.schumaker.force.framework.ioc.annotations.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.force.framework.ioc.reflection.validation.PasswordValidation.PASSWORD_VALIDATION_MESSAGE;

/**
 * The @Password annotation is used to mark a field as password.
 * This annotation can be applied to fields to indicate that the field should be
 * validated as password, minimum 8 characters, 1 capital letter, 1 number and 1 special character.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class User {
 *
 *     @Password
 *     private String password;
 *     // Getters and Setters
 * }
 * }
 * </pre>
 *
 * @see Max
 * @see Min
 * @see Past
 * @see Range
 * @see Future
 * @see NotNull
 * @see NotBlank
 * @see NotEmpty
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String value() default PASSWORD_VALIDATION_MESSAGE;
}