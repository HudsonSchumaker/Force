package br.com.schumaker.force.framework.model;

import br.com.schumaker.force.framework.exception.ForceException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The DateParserTest class.
 * This class is responsible for testing the DateParser class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class DateParserTest {

    @Test
    public void testParseToInstant() {
        // Arrange
        String dateStr = "2021-02-14T03:29:28.259Z";
        Instant expected = Instant.parse(dateStr);

        // Act
        Instant actual = DateParser.parseToInstant(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToLocalDate_ISO() {
        // Arrange
        String dateStr = "2021-02-14";
        LocalDate expected = LocalDate.parse(dateStr);

        // Act
        LocalDate actual = DateParser.parseToLocalDate(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToLocalDate_Custom() {
        // Arrange
        String dateStr = "14/02/2021";
        LocalDate expected = LocalDate.of(2021, 2, 14);

        // Act
        LocalDate actual = DateParser.parseToLocalDate(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToLocalDateTime_ISO() {
        // Arrange
        String dateStr = "2021-02-14T03:29:28";
        LocalDateTime expected = LocalDateTime.parse(dateStr);

        // Act
        LocalDateTime actual = DateParser.parseToLocalDateTime(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToLocalDateTime_Custom() {
        // Arrange
        String dateStr = "2021-02-14T03:29:28.259Z";
        LocalDateTime expected = LocalDateTime.ofInstant(Instant.parse(dateStr), ZoneId.systemDefault());

        // Act
        LocalDateTime actual = DateParser.parseToLocalDateTime(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToDate() {
        // Arrange
        String dateStrISO = "2021-02-14T03:29:28.259Z";
        String dateStrCustom = "14/02/2021";
        Date expectedISO = Date.from(Instant.parse(dateStrISO));
        LocalDateTime localDateTimeCustom = LocalDateTime.of(2021, 2, 14, 0, 0);
        Date expectedCustom = Date.from(localDateTimeCustom.atZone(ZoneId.systemDefault()).toInstant());

        // Act
        Date actualISO = DateParser.parseToDate(dateStrISO);
        Date actualCustom = DateParser.parseToDate(dateStrCustom);

        // Assert
        assertEquals(expectedISO, actualISO);
        assertEquals(expectedCustom, actualCustom);
    }

    @Test
    public void testInvalidDateFormat() {
        // Arrange
        String invalidDateStr = "invalid-date";

        // Act & Assert
        assertThrows(ForceException.class, () -> DateParser.parseToLocalDate(invalidDateStr));
    }
}
