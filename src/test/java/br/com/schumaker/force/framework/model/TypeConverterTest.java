package br.com.schumaker.force.framework.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The TypeConverterTest class.
 * This class is responsible for testing the TypeConverter class.
 *
 * @see TypeConverter
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class TypeConverterTest {

    @Test
    public void testStringConversion() {
        // Arrange
        String value = "test";

        // Act & Assert
        assertEquals(value, TypeConverter.typeParsers.get(String.class).apply(value));
    }

    @Test
    public void testBigIntegerConversion() {
        // Arrange
        String value = "12345678901234567890";
        BigInteger expected = new BigInteger(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(BigInteger.class).apply(value));
    }

    @Test
    public void testBigDecimalConversion() {
        // Arrange
        String value = "12345.6789";
        BigDecimal expected = new BigDecimal(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(BigDecimal.class).apply(value));
    }

    @Test
    public void testIntegerConversion() {
        // Arrange
        String value = "123";
        Integer expected = Integer.parseInt(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Integer.class).apply(value));
    }

    @Test
    public void testFloatConversion() {
        // Arrange
        String value = "123.45";
        Float expected = Float.parseFloat(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Float.class).apply(value));
    }

    @Test
    public void testDoubleConversion() {
        // Arrange
        String value = "123.456";
        Double expected = Double.parseDouble(value);
        assertEquals(expected, TypeConverter.typeParsers.get(Double.class).apply(value));
    }

    @Test
    public void testLongConversion() {
        // Arrange
        String value = "1234567890";
        Long expected = Long.parseLong(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Long.class).apply(value));
    }

    @Test
    public void testBooleanConversion() {
        // Arrange
        String value = "true";
        Boolean expected = true;

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Boolean.class).apply(value));
    }

    @Test
    public void testShortConversion() {
        // Arrange
        String value = "12345";
        Short expected = Short.parseShort(value);
        assertEquals(expected, TypeConverter.typeParsers.get(Short.class).apply(value));
    }

    @Test
    public void testByteConversion() {
        // Arrange
        String value = "123";
        Byte expected = Byte.parseByte(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Byte.class).apply(value));
    }

    @Test
    public void testCharacterConversion() {
        // Arrange
        String value = "a";
        Character expected = value.charAt(0);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Character.class).apply(value));
    }

    @Test
    public void testInstantConversion() {
        // Arrange
        String value = "2021-02-14T03:29:28.259Z";
        Instant expected = Instant.parse(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Instant.class).apply(value));
    }

    @Test
    public void testLocalDateConversion() {
        // Arrange
        String value = "2021-02-14";
        LocalDate expected = LocalDate.parse(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(LocalDate.class).apply(value));
    }

    @Test
    public void testLocalDateTimeConversion() {
        // Arrange
        String value = "2021-02-14T03:29:28";
        LocalDateTime expected = LocalDateTime.parse(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(LocalDateTime.class).apply(value));
    }

    @Test
    public void testUUIDConversion() {
        // Arrange
        String value = "123e4567-e89b-12d3-a456-426614174000";
        UUID expected = UUID.fromString(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(UUID.class).apply(value));
    }

    @Test
    public void testNullConversion() {
        // Arrange, Act & Assert
        assertNull(TypeConverter.convert(String.class, null));
    }

    @Test
    public void testSqlTimestampConversion() {
        // Arrange
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2021-02-14 03:29:28");
        LocalDateTime expected = timestamp.toLocalDateTime();

        // Act
        Object result = TypeConverter.convert(LocalDateTime.class, timestamp);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testSqlTimestampToDateConversion() {
        // Arrange
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2021-02-14 03:29:28");
        java.util.Date expected = new java.util.Date(timestamp.getTime());

        // Act
        Object result = TypeConverter.convert(java.util.Date.class, timestamp);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testUUIDToStringConversion() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String expected = uuid.toString();

        // Act
        Object result = TypeConverter.convert(String.class, uuid);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testNotSupportedTypeConversion() {
        // Arrange
        Object value = new Object();

        // Act
        Object result = TypeConverter.convert(Object.class, value);

        // Assert
        assertEquals(value, result);
    }
}
