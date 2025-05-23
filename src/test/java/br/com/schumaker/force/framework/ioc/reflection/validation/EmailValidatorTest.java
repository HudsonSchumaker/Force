package br.com.schumaker.force.framework.ioc.reflection.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The EmailValidatorTest class.
 * This class is responsible for testing the EmailValidator class.
 *
 * @see EmailValidator
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class EmailValidatorTest {

    @Test
    public void testValidEmail() {
        // Arrange & Act & Assert
        assertTrue(EmailValidator.isValidEmail("test@example.com"));
        assertTrue(EmailValidator.isValidEmail("user.name+tag+sorting@example.com"));
        assertTrue(EmailValidator.isValidEmail("user_name@example.co.in"));
    }

    @Test
    public void testInvalidEmail() {
        // Arrange & Act & Assert
        assertFalse(EmailValidator.isValidEmail("plainaddress"));
        assertFalse(EmailValidator.isValidEmail("@missingusername.com"));
        assertFalse(EmailValidator.isValidEmail("username@.com"));
        assertFalse(EmailValidator.isValidEmail("username@.com."));
        assertFalse(EmailValidator.isValidEmail("username@com"));
    }

    @Test
    public void testNullEmail() {
        // Arrange & Act & Assert
        assertFalse(EmailValidator.isValidEmail(null));
    }
}