package br.com.schumaker.force.framework.web.view;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;

import java.util.Set;

/**
 * The Controllers class.
 * This class is responsible for retrieving the allowed HTTP methods for a given controller.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class Controllers {
    public static final String CONTROLLER_NOT_FOUND = "Controller not found.";

    /**
     * Get the allowed HTTP methods for a given controller class.
     *
     * @param controllerClass the controller class
     * @return a set of allowed HTTP methods
     */
    public static Set<String> getAllowedMethods(Class<?> controllerClass) {
        var controller = IoCContainer.getInstance().getController(controllerClass);
        if (controller == null) {
            throw new ForceException("Controller not found: " + controllerClass.getName());
        }

        return controller.getSupportedMethods();
    }
}
