package br.com.schumaker.force.framework.ioc.reflection.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The PasswordValidationTest class.
 * This class is responsible for testing the PasswordValidator class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PasswordValidationTest {

    /**
     * Test the password validation.
     * This method tests the password validation with various test cases.
     */
    @Test
    public void testPasswordValidation() {
        // Arrange
        String validPassword = "Valid1@Password";
        String invalidPassword = "invalidpassword";
        String shortPassword = "Short1@";
        String noUpperCasePassword = "nouppercase1@";
        String noDigitPassword = "NoDigit@Password";
        String noSpecialCharPassword = "NoSpecialChar1";

        // Act & Assert
        assertTrue(PasswordValidator.isValidPassword(validPassword));
        assertFalse(PasswordValidator.isValidPassword(invalidPassword));
        assertFalse(PasswordValidator.isValidPassword(shortPassword));
        assertFalse(PasswordValidator.isValidPassword(noUpperCasePassword));
        assertFalse(PasswordValidator.isValidPassword(noDigitPassword));
        assertFalse(PasswordValidator.isValidPassword(noSpecialCharPassword));
    }
}
