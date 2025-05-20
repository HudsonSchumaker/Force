package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.controller.Controller;
import br.com.schumaker.force.framework.ioc.annotations.controller.Get;
import br.com.schumaker.force.framework.ioc.annotations.controller.Options;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.MockHttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OptionsHandlerTest {
    private MockHttpExchange exchange;

    @BeforeEach
    public void setUp() {
        exchange = new MockHttpExchange();
    }

    @Test
    public void testProcessRequest() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyController.class));
        String fullUrl = "http://localhost:8080/context/controller/";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new OptionsHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.httpCode());
    }

    @Test
    public void dotNotFindController() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyController.class));
        String fullUrl = "http://localhost:8080/context/invalid-controller/";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new OptionsHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.httpCode());
    }

    // Test controller class
    @Controller("/controller")
    public static class MyController {
        @Get("/users/{id}")
        public String getUserById(String id) {
            return "User: " + id;
        }

        @Options
        public String getOptions(HttpRequestHeader headers) {
            return "Options";
        }
    }
}
