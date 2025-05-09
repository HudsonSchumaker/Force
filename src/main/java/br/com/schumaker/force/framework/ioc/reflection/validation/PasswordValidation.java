package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.annotations.validations.Password;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.web.http.Http;

import java.lang.reflect.Field;

/**
 * The PasswordValidation class.
 * This class is used to validate that the specified field is a valid password.
 * @see Password
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PasswordValidation implements Validation {
    public static final String PASSWORD_VALIDATION_MESSAGE = "Field %s is not a valid password. " +
            "Must contain at least 8 digits, one uppercase letter, one digit, and one special character.";


    @Override
    public void validate(Object object, Field field) throws ForceException {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue instanceof String password) {
                if (!PasswordValidator.isValidPassword(password)) {
                    var message = field.getAnnotation(Password.class).value();
                    if (message.equals(PASSWORD_VALIDATION_MESSAGE)) {
                        throw new ForceException(String.format(message, field.getName()), Http.HTTP_400);
                    }
                    throw new ForceException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ForceException("Failed to validate field " + field.getName(), e);
        } finally {
            field.setAccessible(false);
        }
    }
}
