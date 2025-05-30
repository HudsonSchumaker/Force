package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.ioc.annotations.validations.Range;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.web.http.Http;

import java.lang.reflect.Field;

/**
 * The RangeValidation class.
 * This class is used to validate that the specified field is inside a range.
 *
 * @see Range
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class RangeValidation implements Validation {
    public static final String RANGE_VALIDATION_MESSAGE = "Field %s is out of range %s to %s.";

    /**
     * Validate the object field.
     *
     * @param object the object to be validated.
     * @param field the field to be validated.
     * @throws ForceException if the field is out of range.
     */
    @Override
    public void validate(Object object, Field field) throws ForceException {
        try {
            field.setAccessible(true);
            double minValue = field.getAnnotation(Range.class).min();
            double maxValue = field.getAnnotation(Range.class).max();
            Object fieldValue = field.get(object);
            if (fieldValue instanceof Number) {
                double value = ((Number) fieldValue).doubleValue();
                if (value < minValue || value > maxValue) {
                    var message = field.getAnnotation(Range.class).message();
                    if (message.equals(RANGE_VALIDATION_MESSAGE)) {
                        throw new ForceException(String.format(message, field.getName(), minValue, maxValue), Http.HTTP_400);
                    }
                    throw new ForceException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ForceException("Failed to validate: " + field.getName(), e);
        } finally {
            field.setAccessible(false);
        }
    }
}
