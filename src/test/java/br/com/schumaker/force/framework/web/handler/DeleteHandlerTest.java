package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.controller.*;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.MockHttpExchange;
import br.com.schumaker.force.framework.web.view.ResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteHandlerTest {
    private MockHttpExchange exchange;

    @BeforeEach
    public void setUp() {
        exchange = new MockHttpExchange();
    }

    @Test
    public void testProcessRequest() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyDeleteController.class));
        String fullUrl = "http://localhost:8080/context/delete/1";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new DeleteHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.httpCode());
    }

    @Test
    public void testNotFindController() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyDeleteController.class));
        String fullUrl = "http://localhost:8080/context/invalid-controller/1";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new DeleteHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.httpCode());
    }
    
    // Test controller class
    @Controller("/delete")
    public static class MyDeleteController {
        @Delete("/{id}")
        public ResponseView<Void> getOptions(@PathVariable BigInteger id, HttpRequestHeader headers) {
            return ResponseView.noContent().build();
        }
    }
}
