package br.com.schumaker.force.framework.ioc.managed;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.annotations.db.Column;
import br.com.schumaker.force.framework.ioc.annotations.db.Pk;
import br.com.schumaker.force.framework.ioc.annotations.db.Table;
import br.com.schumaker.force.framework.jdbc.SqlCrud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagedRepositoryTest {
    private ManagedRepository managedRepository;

    @BeforeEach
    void setUp() {
        managedRepository = ManagedRepository.builder(MyRepository.class);
    }

    @Test
    void testGetFqn() {
        assertEquals("br.com.schumaker.force.framework.ioc.managed.ManagedRepositoryTest$MyRepository", managedRepository.getFqn());
    }

    @Test
    void testGetInstance() {
        assertNotNull(managedRepository.getInstance());
        assertInstanceOf(MyRepository.class, managedRepository.getInstance());
    }

    @Test
    void testInvalidRepository() {
        Exception exception = assertThrows(ForceException.class, () -> ManagedRepository.builder(InvalidRepository.class));
        assertEquals("Repository class does not have a parameterized superinterface of type SqlCrud.", exception.getMessage());
    }

    // Test repository class
    public interface MyRepository extends SqlCrud<Long, MyEntity> {}

    // Invalid repository class
    public interface InvalidRepository {}

    // Test entity class
    @Table("my_entity")
    public static class MyEntity {
        @Pk
        private Long id;

        @Column
        private String name;
    }
}