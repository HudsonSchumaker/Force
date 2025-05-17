package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.controller.Get;
import br.com.schumaker.force.framework.ioc.annotations.controller.Options;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpResponse;

import static br.com.schumaker.force.framework.web.http.Http.HTTP_HEADER;
import static br.com.schumaker.force.framework.web.http.Http.HTTP_OPTIONS;

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
        String controllerRoute = routeAndMethodPath.first();
        String methodPath = routeAndMethodPath.second();

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            try {
                var mappingAndMethodAndParams = controller.getMethod(methodPath, HTTP_OPTIONS);
                var method = mappingAndMethodAndParams.second();
                var parameters = mappingAndMethodAndParams.third();
                var arguments = new Object[parameters.size()];
                var defaultHttpCode = method.getAnnotation(Options.class).httpCode();
                var applicationType = method.getAnnotation(Options.class).type();

                Object result = method.invoke(controller.getInstance(), arguments);
                var methodReturnType = method.getReturnType();
                return new HttpResponse(methodReturnType, result, defaultHttpCode, applicationType, request.exchange());
            } catch (Exception ex) {
                throw new ForceException("Error invoking method.", ex);
            }
        } else {
            var httpCode = Http.HTTP_404;
            var response = "Controller not found.";
            return new HttpResponse(String.class, response, httpCode, Http.APPLICATION_JSON, request.exchange());
        }
    }
}
