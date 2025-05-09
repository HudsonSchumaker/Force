package br.com.schumaker.force.framework.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ModelViewMapperTest {

    @Test
    public void testFrom() {
        ModelViewMapper<SourceType, TargetType> mapper = new ModelViewMapperImpl();
        SourceType source = new SourceType("Test");
        TargetType target = mapper.from(source);
        assertNotNull(target);
        assertEquals(source.getName(), target.getName());
    }

    static class ModelViewMapperImpl implements ModelViewMapper<SourceType, TargetType> {
        @Override
        public TargetType from(SourceType source) {
            return new TargetType(source.name);
        }
    }

    static class SourceType {
       String name;

       public SourceType(String name) {
           this.name = name;
       }

       public String getName() {
              return name;
       }
    }

    static class TargetType {
         String name;

         public TargetType(String name) {
              this.name = name;
         }

         public String getName() {
                  return name;
         }

         public void setName(String name) {
                  this.name = name;
         }
    }
}
