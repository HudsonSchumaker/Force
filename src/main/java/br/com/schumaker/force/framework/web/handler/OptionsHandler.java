package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.controller.Options;
import br.com.schumaker.force.framework.ioc.managed.ManagedController;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.HttpResponse;

import static br.com.schumaker.force.framework.web.view.Controllers.CONTROLLER_NOT_FOUND;

/**
 * The OptionsHandler class.
 * This class is responsible for processing HTTP_OPTIONS requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class OptionsHandler implements RequestHandler {
    private static final IoCContainer container = IoCContainer.getInstance();

    /**
     * Process the HTTP_OPTIONS request.
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

    /**
     * Process the controller request for HTTP_OPTIONS method.
     *
     * @param controller   the managed controller.
     * @param methodPath   the method path.
     * @param request      the HTTP request.
     * @return the HTTP response.
     */
    private HttpResponse processControllerRequest(ManagedController controller, String methodPath, HttpRequest request) {
        var mappingAndMethodAndParams = controller.getMethod(methodPath, Http.HTTP_OPTIONS);
        var method = mappingAndMethodAndParams.second();
        var parameters = mappingAndMethodAndParams.third();
        var defaultHttpCode = method.getAnnotation(Options.class).httpCode();
        var applicationType = method.getAnnotation(Options.class).type();

        var arguments = new Object[parameters.size()];
        try {
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).getType().equals(HttpRequestHeader.class)) {
                    arguments[i] = new HttpRequestHeader(request.getRequestHeaders());
                }
            }

            Object result = method.invoke(controller.getInstance(), arguments);
            return createSuccessResponse(method.getReturnType(), result, defaultHttpCode, applicationType, request);
        } catch (Exception ex) {
            throw new ForceException("Error invoking method: " + method.getName(), ex);
        }
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
