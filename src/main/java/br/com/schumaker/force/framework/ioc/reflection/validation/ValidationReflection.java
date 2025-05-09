package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.ioc.annotations.validations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ValidationReflection class provides utility methods for validating objects using reflection.
 * It validates fields annotated with validation annotations such as @NotNull, @NotBlank, @NotEmpty, @Email
 * , @Min, @Max, @Range, @Past and @Future.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @see NotNull
 * @see NotBlank
 * @see NotEmpty
 * @see Email
 * @see Min
 * @see Max
 * @see Range
 * @see Past
 * @see Future
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ValidationReflection {
    private static final ValidationReflection INSTANCE = new ValidationReflection();
    private final Map<Class<? extends Annotation>, Validation> validationStrategies = new HashMap<>();

    /**
     * Constructs a new ValidationReflection instance.
     */
    private ValidationReflection() {
        validationStrategies.put(Email.class, new EmailValidation());
        validationStrategies.put(NotNull.class, new NotNullValidation());
        validationStrategies.put(NotBlank.class, new NotBlankValidation());
        validationStrategies.put(NotEmpty.class, new NotEmptyValidation());
        validationStrategies.put(Min.class, new MinValidation());
        validationStrategies.put(Max.class, new MaxValidation());
        validationStrategies.put(Range.class, new RangeValidation());
        validationStrategies.put(Past.class, new PastValidation());
        validationStrategies.put(Future.class, new FutureValidation());
        validationStrategies.put(Regex.class, new RegexValidation());
        validationStrategies.put(Password.class, new PasswordValidation());
    }

    public static ValidationReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Validates the object using reflection.
     *
     * @param object the object to be validated.
     */
    public void validate(Object object) {
        List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
        fields.forEach(field -> {
            for (Annotation annotation : field.getAnnotations()) {
                var strategy = validationStrategies.get(annotation.annotationType());
                if (strategy != null) {
                    strategy.validate(object, field);
                }
            }
        });
    }
}
