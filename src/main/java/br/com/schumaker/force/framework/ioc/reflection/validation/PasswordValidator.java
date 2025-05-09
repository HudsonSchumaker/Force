package br.com.schumaker.force.framework.ioc.reflection.validation;

/**
 * The PasswordValidator class.
 * This class is responsible for validating passwords.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PasswordValidator {
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+[]{}|;:,.<>?";

    /**
     * Validate a password.
     * This method checks if the password meets the following criteria:
     * - At least 8 characters long
     * - Contains at least one uppercase letter
     * - Contains at least one digit
     * - Contains at least one special character
     *
     * @param password the password to be validated.
     * @return true if the password is valid, false otherwise.
     */
    public static boolean isValidPassword(String password) {
        boolean lengthValid = password.length() >= PASSWORD_MIN_LENGTH;
        boolean upperCaseValid = password.chars().anyMatch(Character::isUpperCase);
        boolean digitValid = password.chars().anyMatch(Character::isDigit);
        boolean specialCharValid = password.chars().anyMatch(ch -> SPECIAL_CHARACTERS.indexOf(ch) >= 0);

        return lengthValid && upperCaseValid && digitValid && specialCharValid;
    }
}
