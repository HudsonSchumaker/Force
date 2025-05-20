package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.ioc.annotations.controller.Delete;
import br.com.schumaker.force.framework.ioc.annotations.controller.PathVariable;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.managed.ManagedController;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.HttpResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static br.com.schumaker.force.framework.web.handler.AbstractRequestHandler.container;
import static br.com.schumaker.force.framework.web.http.Http.HTTP_DELETE;
import static br.com.schumaker.force.framework.web.view.Controllers.CONTROLLER_NOT_FOUND;

/**
 * The DeleteHandler class.
 * This class is responsible for processing HTTP_DELETE requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class DeleteHandler implements RequestHandler {

    /**
     * Process the HTTP_DELETE request.
     *
     * @param request the HTTP request.
     * @return the HTTP response.
     */
    @Override
    public HttpResponse processRequest(HttpRequest request) {
        var routeAndMethodPath = request.getControllerRouteAndMethodPath();
        var controllerRoute = routeAndMethodPath.first();
        var methodPath = routeAndMethodPath.second();

        var controller = container.getController(controllerRoute);
        if (controller == null) {
            return createErrorResponse();
        }

        return processControllerRequest(controller, methodPath, request);
    }

    private HttpResponse processControllerRequest(ManagedController controller, String methodPath, HttpRequest request) {
        var mappingAndMethodAndParams = controller.getMethod(methodPath, HTTP_DELETE);
        var mapping = mappingAndMethodAndParams.first();
        var method = mappingAndMethodAndParams.second();
        var parameters = mappingAndMethodAndParams.third();
        var applicationType = method.getAnnotation(Delete.class).type();
        var defaultHttpCode = method.getAnnotation(Delete.class).httpCode();
        var pathVariables = controller.extractPathVariables(mapping, methodPath);
        var arguments = this.getArguments(parameters, pathVariables, method, request);

        try {
            Object result = method.invoke(controller.getInstance(), arguments);
            return createSuccessResponse(method.getReturnType(), result, defaultHttpCode, applicationType, request);
        } catch (Exception ex) {
            throw new ForceException("Error invoking method: " + method.getName(), ex);
        }
    }

    /**
     * Get the arguments for the method invocation.
     *
     * @param parameters      the method parameters.
     * @param pathVariables   the path variables.
     * @param method          the method to be invoked.
     * @param request         the HTTP request.
     * @return an array of arguments for the method invocation.
     */
    private Object[] getArguments(List<Parameter> parameters, List<Object> pathVariables, Method method, HttpRequest request) {
        var arguments = new Object[parameters.size()];
        try {
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).isAnnotationPresent(PathVariable.class)) {
                    arguments[i] = AbstractRequestHandler.convertToType(pathVariables.get(i), parameters.get(i).getType());
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
