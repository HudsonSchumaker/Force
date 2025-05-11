package br.com.schumaker.force.framework.web.view;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The ResponseViewTest class.
 * This class is responsible for testing the ResponseView class.
 *
 * @see ResponseView
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ResponseViewTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange & Act
        ResponseView<String> responseView = new ResponseView<>();

        // Assert
        assertEquals(204, responseView.getHttpCode());
        assertNull(responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testHttpCodeConstructor() {
        // Arrange & Act
        ResponseView<String> responseView = new ResponseView<>(200);

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertNull(responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testHttpCodeAndBodyConstructor() {
        // Arrange & Act
        ResponseView<String> responseView = new ResponseView<>(200, "Test Body");

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testHttpCodeAndHeadersConstructor() {
        // Arrange
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Act
        ResponseView<String> responseView = new ResponseView<>(200, headers);

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertNull(responseView.getBody());
        assertEquals(headers, responseView.getHeaders());
    }

    @Test
    public void testFullConstructor() {
        // Arrange
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Act
        ResponseView<String> responseView = new ResponseView<>(200, "Test Body", headers);

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertEquals(headers, responseView.getHeaders());
    }

    @Test
    public void testStaticOkBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.ok().body("Test Body").build();

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticCreatedBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.created().body("Test Body").build();

        // Assert
        assertEquals(201, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticBadRequestBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.badRequest().body("Test Body").build();

        // Assert
        assertEquals(400, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticNoContentBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.noContent().build();

        // Assert
        assertEquals(204, responseView.getHttpCode());
        assertNull(responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticAcceptedBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.accepted().body("Test Body").build();

        // Assert
        assertEquals(202, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticNotFoundBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.notFound().body("Test Body").build();

        // Assert
        assertEquals(404, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticInternalServerErrorBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.internalServerError().body("Test Body").build();

        // Assert
        assertEquals(500, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticUnauthorizedBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.unauthorized().body("Test Body").build();

        // Assert
        assertEquals(401, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticForbiddenBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.forbidden().body("Test Body").build();

        // Assert
        assertEquals(403, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticConflictBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.conflict().body("Test Body").build();

        // Assert
        assertEquals(409, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }
}
