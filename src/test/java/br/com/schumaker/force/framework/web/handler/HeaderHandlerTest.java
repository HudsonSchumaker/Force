package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.controller.*;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.MockHttpExchange;
import br.com.schumaker.force.framework.web.view.ResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HeaderHandlerTest {
    private MockHttpExchange exchange;

    @BeforeEach
    public void setUp() {
        exchange = new MockHttpExchange();
    }

    @Test
    public void testProcessRequest() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyHeaderController.class));
        String fullUrl = "http://localhost:8080/context/header/1";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new HeaderHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.httpCode());
    }

    @Test
    @Disabled("Ignoring this test for now")
    public void testProcessRequestWithQuery() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyHeaderController.class));
        String fullUrl = "http://localhost:8080/context/header/query?name=Xxxxx";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new HeaderHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.httpCode());
    }

    @Test
    public void dotNotFindController() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyHeaderController.class));
        String fullUrl = "http://localhost:8080/context/invalid-controller/1";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new HeaderHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.httpCode());
    }

    // Test controller class
    @Controller("/header")
    public static class MyHeaderController {
        @Header("/{id}")
        public ResponseView<Void> getHeaders(@PathVariable BigInteger id, HttpRequestHeader headers) {
            return ResponseView
                    .noContent()
                    .build();
        }

        @Header("/query")
        public ResponseView<Void> getHeadersWithQuery(@QueryParam(value = "name", required = false, defaultValue = "Guest") String name) {
            return ResponseView
                    .noContent()
                    .build();
        }
    }
}
