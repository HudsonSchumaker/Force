package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.controller.Header;
import br.com.schumaker.force.framework.ioc.annotations.controller.PathVariable;
import br.com.schumaker.force.framework.ioc.annotations.controller.QueryParam;
import br.com.schumaker.force.framework.ioc.managed.ManagedController;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.HttpResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

import static br.com.schumaker.force.framework.web.http.Http.HTTP_HEADER;
import static br.com.schumaker.force.framework.web.view.Controllers.CONTROLLER_NOT_FOUND;

/**
 * The HeaderHandler class.
 * This class is responsible for processing HTTP_HEADER requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class HeaderHandler implements RequestHandler {
    private final IoCContainer container = IoCContainer.getInstance();

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        var routeAndMethodPath = request.getControllerRouteAndMethodPath();
        var controllerRoute = routeAndMethodPath.first();
        var methodPath = routeAndMethodPath.second();

        var controller = container.getController(controllerRoute);
        if (controller == null) {
            return this.createErrorResponse();
        }

        return this.processControllerRequest(controller, methodPath, request);
    }

    private HttpResponse processControllerRequest(ManagedController controller, String methodPath, HttpRequest request) {
        var mappingAndMethodAndParams = controller.getMethod(methodPath, HTTP_HEADER);
        var mapping = mappingAndMethodAndParams.first();
        var method = mappingAndMethodAndParams.second();
        var parameters = mappingAndMethodAndParams.third();
        var pathVariables = controller.extractPathVariables(mapping, methodPath);
        var queryParameters = request.getQueryParams();
        var arguments = this.getArguments(parameters, pathVariables, queryParameters, mappingAndMethodAndParams.second(), request);

        try {
            var defaultHttpCode = method.getAnnotation(Header.class).httpCode();
            var applicationType = method.getAnnotation(Header.class).type();

            Object result = method.invoke(controller.getInstance(), arguments);
            return this.createSuccessResponse(method.getReturnType(), result, defaultHttpCode, applicationType, request);
        } catch (Exception ex) {
            throw new ForceException("Error invoking method: " + method.getName(), ex);
        }
    }

    /**
     * Get the arguments for the method invocation.
     *
     * @param parameters      the method parameters.
     * @param pathVariables   the path variables.
     * @param queryParameters the query parameters.
     * @param method          the method to be invoked.
     * @param request         the HTTP request.
     * @return an array of arguments for the method invocation.
     * @throws ForceException if an error occurs while processing the request.
     */
    private Object[] getArguments(List<Parameter> parameters, List<Object> pathVariables, Map<String, String> queryParameters, Method method, HttpRequest request) {
        var arguments = new Object[parameters.size()];
        try {
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).isAnnotationPresent(PathVariable.class)) {
                    arguments[i] = AbstractRequestHandler.convertToType(pathVariables.get(i), parameters.get(i).getType());
                    continue;
                }

                if (parameters.get(i).isAnnotationPresent(QueryParam.class)) {
                    var queryParams = parameters.get(i).getAnnotation(QueryParam.class);
                    var paramName = queryParams.value();
                    var defaultValue = queryParams.defaultValue();
                    var required = queryParams.required();

                    var value = queryParameters.getOrDefault(paramName, defaultValue);
                    if (required && value.isEmpty()) {
                        throw new ForceException("Missing required query parameter: " + paramName);
                    }

                    arguments[i] = AbstractRequestHandler.convertToType(value, parameters.get(i).getType());
                    continue;
                }

                if (parameters.get(i).getType().equals(HttpRequestHeader.class)) {
                    arguments[i] = new HttpRequestHeader(request.getRequestHeaders());
                }
            }
        } catch (Exception ex) {
            throw new ForceException("Error processing request, method: " + method.getName(), ex);
        }

        return arguments;
    }

    /**
     * Create a success response.
     *
     * @param returnType the return type of the method.
     * @param result     the result of the method invocation.
     * @param httpCode   the HTTP status code.
     * @param type       the content type of the response.
     * @param request    the HTTP request.
     * @return the success response.
     */
    private HttpResponse createSuccessResponse(Class<?> returnType, Object result, int httpCode, String type, HttpRequest request) {
        return new HttpResponse(returnType, result, httpCode, type, request.exchange());
    }

    /**
     * Create an error response.
     *
     * @return the error response.
     */
    private HttpResponse createErrorResponse() {
        return new HttpResponse(String.class, CONTROLLER_NOT_FOUND, Http.HTTP_404, Http.APPLICATION_JSON, null);
    }
}
