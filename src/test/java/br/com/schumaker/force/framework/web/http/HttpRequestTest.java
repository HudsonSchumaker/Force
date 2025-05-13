package br.com.schumaker.force.framework.web.http;

import br.com.schumaker.force.framework.model.Pair;
import com.sun.net.httpserver.Headers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTest {
    private MockHttpExchange exchange;

    @BeforeEach
    public void setUp() {
        exchange = new MockHttpExchange();
    }

    @Test
    public void testGetControllerRouteAndMethodPath() {
        String fullUrl = "http://localhost:8080/payment-api/controller/method";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        Pair<String, String> result = request.getControllerRouteAndMethodPath();

        assertEquals("/controller", result.first());
        assertEquals("/method", result.second());
    }

    @Test
    void testGetRequestHeaders() {
        Headers headers = new Headers();
        headers.put("Content-Type", List.of("application/json"));
        headers.put("Authorization", List.of("Bearer token"));
        exchange.setRequestHeaders(headers);

        HttpRequest request = new HttpRequest("http://localhost:8080", exchange);
        Map<String, String> result = request.getRequestHeaders();

        assertEquals(2, result.size());
        assertEquals("application/json", result.get("Content-type"));
        assertEquals("Bearer token", result.get("Authorization"));
    }

    @Test
    void testReadRequestBody() throws Exception {
        String body = "{\"key\":\"value\"}";
        InputStream inputStream = new ByteArrayInputStream(body.getBytes());
        exchange.setRequestBody(inputStream);

        HttpRequest request = new HttpRequest("http://localhost:8080", exchange);
        String result = request.readRequestBody();

        assertEquals(body, result);
    }

    @Test
    void testGetQueryParams() {
        String fullUrl = "http://localhost:8080/api/v1/controller?key1=value1&key2=value2";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        Map<String, String> result = request.getQueryParams();

        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }

    @Test
    void testGetQueryParams_Empty() {
        String fullUrl = "http://localhost:8080/api/v1/controller";
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        Map<String, String> result = request.getQueryParams();

        assertTrue(result.isEmpty());
    }
}