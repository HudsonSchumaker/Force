package br.com.schumaker.force.framework.ioc.managed;

import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.exception.ExceptionHandler;
import br.com.schumaker.force.framework.ioc.annotations.exception.GlobalExceptionHandler;
import br.com.schumaker.force.framework.model.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ManagedGlobalExceptionHandlerTest class is responsible for testing the ManagedGlobalExceptionHandler class.
 * It includes tests for the getFqn, getInstance, and geMethod methods.
 *
 * @see ManagedGlobalExceptionHandler
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ManagedGlobalExceptionHandlerTest {

    @BeforeEach
    void setUp() {
        Class<?> handlerClass = MyExceptionHandler.class;
        IoCContainer.getInstance().registerGlobalExceptionHandler(List.of(handlerClass));
    }

    @Test
    void testGetFqn() {
        var managedGlobalExceptionHandler = IoCContainer.getInstance().getGlobalExceptionHandler();
        assertEquals("br.com.schumaker.force.framework.ioc.managed.ManagedGlobalExceptionHandlerTest$MyExceptionHandler",
                managedGlobalExceptionHandler.getFqn());
    }

    @Test
    void testGetInstance() {
        assertNotNull(IoCContainer.getInstance().getGlobalExceptionHandler().getInstance());
        assertInstanceOf(MyExceptionHandler.class, IoCContainer.getInstance().getGlobalExceptionHandler().getInstance());
    }

    @Test
    void testGeMethod() {
        var managedGlobalExceptionHandler = IoCContainer.getInstance().getGlobalExceptionHandler();
        Pair<Method, List<Parameter>> methodPair = managedGlobalExceptionHandler.geMethod(MyException.class);
        assertNotNull(methodPair);
        assertNotNull(methodPair.first());
        assertNotNull(methodPair.second());
    }

    // Test handler class
    @GlobalExceptionHandler
    public static class MyExceptionHandler {
        @ExceptionHandler(MyException.class)
        public void handleException(MyException e) {
            // handle exception
        }
    }

    // Test exception class
    public static class MyException extends RuntimeException {}
}