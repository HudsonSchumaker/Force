package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ControllerReflection;
import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.reflection.Triple;
import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The ManagedController class represents a managed controller within the IoC container.
 * It provides methods to retrieve the fully qualified name (FQN), route, and instance of the managed controller.
 * Additionally, it provides methods to retrieve specific HTTP methods based on the mapping and type.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ManagedController {
    private final String fqn;
    private final String route;
    private final Map<String, List<Triple<String, Method, List<Parameter>>>> methods;  // <VERB, List<Triple<mapping, method, List<parameters>>>>
    private Object instance;

    private ManagedController(String fqn, String route, Map<String, List<Triple<String, Method, List<Parameter>>>> methods) {
        this.fqn = fqn;
        this.route = route;
        this.methods = methods;
    }

    /**
     * Creates a new ManagedController instance using the specified controller class.
     *
     * @param controller the controller class
     * @return a new ManagedController instance
     */
    public static ManagedController builder(Class<?> controller) {
        var fqn = controller.getName();
        var route = ControllerReflection.getControllerRoute(controller);
        var methods = ControllerReflection.getMethods(controller);

        var managedController = new ManagedController(fqn, route, methods);
        managedController.instance = ClassReflection.getInstance().instantiate(controller);

        return managedController;
    }

    public Triple<String, Method, List<Parameter>> getMethod(String mapping, String type) {
        if (type.equalsIgnoreCase(Http.GET)) {
            return search(mapping, Http.GET);
        }

        if (type.equalsIgnoreCase(Http.POST)) {
            return search(mapping, Http.POST);
        }

        if (type.equalsIgnoreCase(Http.PUT)) {
            return search(mapping, Http.PUT);
        }

        if (type.equalsIgnoreCase(Http.PATCH)) {
            return search(mapping, Http.PATCH);
        }

        if (type.equalsIgnoreCase(Http.DELETE)) {
            return search(mapping, Http.DELETE);
        }

        throw new RuntimeException(Http.HTTP_501_NOT_IMPLEMENTED);
    }

    private Triple<String, Method, List<Parameter>> search(String mapping, String type) {
        var getMethods = methods.get(type);
        for (var mappingAndMethodAndParams : getMethods) {
            if (this.pathMatches(mappingAndMethodAndParams.first(), mapping)) {
                return mappingAndMethodAndParams;
            }
        }

        throw new RuntimeException(Http.HTTP_404_NOT_FOUND);
    }

    private boolean pathMatches(String pattern, String path) {
        var patternParts = splitAndFilter(pattern);
        var pathParts = splitAndFilter(path);

        if (patternParts.size() != pathParts.size()) {
            return false;
        }

        for (int i = 0; i < patternParts.size(); i++) {
            if (!isPathVariable(patternParts.get(i)) && !patternParts.get(i).equals(pathParts.get(i))) {
                return false;
            }
        }

        return true;
    }

    public List<Object> extractPathVariables(String pattern, String path) {
        var patternParts = splitAndFilter(pattern);
        var pathParts = splitAndFilter(path);

        List<Object> variables = new ArrayList<>();
        for (int i = 0; i < patternParts.size(); i++) {
            if (isPathVariable(patternParts.get(i))) {
                variables.add(pathParts.get(i));
            }
        }

        return variables;
    }

    private List<String> splitAndFilter(String input) {
        return Arrays.stream(input.split("/"))
                .filter(part -> !part.isEmpty())
                .toList();
    }

    private boolean isPathVariable(String part) {
        return part.startsWith("{") && part.endsWith("}");
    }

    public String getFqn() {
        return fqn;
    }

    public String getRoute() {
        return route;
    }

    public Object getInstance() {
        return instance;
    }
}
