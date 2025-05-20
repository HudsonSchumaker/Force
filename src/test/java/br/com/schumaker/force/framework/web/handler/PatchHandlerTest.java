package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.controller.Controller;
import br.com.schumaker.force.framework.ioc.annotations.controller.Patch;
import br.com.schumaker.force.framework.ioc.annotations.controller.PathVariable;
import br.com.schumaker.force.framework.ioc.annotations.controller.Payload;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.MockHttpExchange;
import br.com.schumaker.force.framework.web.view.ResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PatchHandlerTest {
    private MockHttpExchange exchange;

    @BeforeEach
    public void setUp() {
        exchange = new MockHttpExchange();
    }

    @Test
    public void testProcessRequest() {
        // Arrange
        String body = "{\"key\":\"value\"}";
        InputStream inputStream = new ByteArrayInputStream(body.getBytes());
        exchange.setRequestBody(inputStream);

        IoCContainer.getInstance().registerController(List.of(MyPatchController.class));
        String fullUrl = "http://localhost:8080/context/patch/1";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new PatchHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(202, response.httpCode());
    }

    @Test
    public void testNotFindController() {
        // Arrange
        IoCContainer.getInstance().registerController(List.of(MyPatchController.class));
        String fullUrl = "http://localhost:8080/context/invalid-controller/1";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        // Act
        var handler = new PatchHandler();
        var response = handler.processRequest(request);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.httpCode());
    }

    // Test controller class
    @Controller("/patch")
    public static class MyPatchController {
        @Patch("/{id}")
        public ResponseView<Void> patch(@PathVariable("id") BigInteger id, @Payload Map<String, Object> patch, HttpRequestHeader headers) {
            return ResponseView
                    .accepted()
                    .body(null)
                    .headers("info", "patch")
                    .build();
        }
    }
}
