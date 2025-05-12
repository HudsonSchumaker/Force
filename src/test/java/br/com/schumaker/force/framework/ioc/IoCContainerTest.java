package br.com.schumaker.force.framework.ioc;

import br.com.schumaker.force.framework.ioc.annotations.bean.Configuration;
import br.com.schumaker.force.framework.ioc.annotations.bean.Service;
import br.com.schumaker.force.framework.ioc.annotations.controller.Controller;
import br.com.schumaker.force.framework.ioc.annotations.db.Pk;
import br.com.schumaker.force.framework.ioc.annotations.db.Repository;
import br.com.schumaker.force.framework.ioc.annotations.db.Table;
import br.com.schumaker.force.framework.ioc.annotations.exception.GlobalExceptionHandler;
import br.com.schumaker.force.framework.ioc.managed.ManagedGlobalExceptionHandler;
import br.com.schumaker.force.framework.ioc.managed.ManagedGlobalExceptionHandlerTest;
import br.com.schumaker.force.framework.jdbc.SqlCrud;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IoCContainerTest {

    @Test
    public void testGetInstance() {
        // Arrange and Act
        IoCContainer instance = IoCContainer.getInstance();

        // Assert
        assertNotNull(instance);
    }

    @Test
    public void testGetGlobalExceptionHandler() {
        // Arrange
        Class<?> handlerClass = ManagedGlobalExceptionHandlerTest.MyExceptionHandler.class;
        IoCContainer.getInstance().registerGlobalExceptionHandler(List.of(handlerClass));
        IoCContainer instance = IoCContainer.getInstance();

        // Act
        ManagedGlobalExceptionHandler handler = instance.getGlobalExceptionHandler();

        // Assert
        assertNotNull(handler);
    }

    @Test
    public void testGetController() {
        // Arrange
        String url = "/test";
        IoCContainer.getInstance().registerController(List.of(MyController.class));

        // Act
        var controller = IoCContainer.getInstance().getController(url);

        // Assert
        assertNotNull(controller);
    }

    @Test
    public void testGetConfiguration() {
        // Arrange
        String fqn = MyConfiguration.class.getName();
        IoCContainer.getInstance().registerConfiguration(List.of(MyConfiguration.class));

        // Act
        var configuration = IoCContainer.getInstance().getConfiguration(fqn);

        // Assert
        assertNotNull(configuration);
    }

    @Test
    public void testGetRepository() {
        // Arrange
        String fqn = MyRepository.class.getName();
        IoCContainer.getInstance().registerRepository(List.of(MyRepository.class));

        // Act
        var repository = IoCContainer.getInstance().getRepository(fqn);

        // Assert
        assertNotNull(repository);
    }

    @Test
    public void testGetService() {
        // Arrange
        String fqn = MyService.class.getName();
        IoCContainer.getInstance().registerService(List.of(MyService.class));

        // Act
        var service = IoCContainer.getInstance().getService(fqn);

        // Assert
        assertNotNull(service);
    }

    @GlobalExceptionHandler
    public static class MyExceptionHandler {}

    @Controller("/test")
    public static class MyController {}

    @Configuration
    public static class MyConfiguration {}

    @Table
    public static class MyTable {
        @Pk("id")
        private BigInteger id;
    }

    @Repository
    public interface MyRepository extends SqlCrud<BigInteger, MyTable> {}

    @Service
    public static class MyService {}
}
