import org.example.ClassGenerator;
import org.example.SchemaField;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ClassGeneratorTest {
    @Test
    public void testGenerateClass() {
        String className = "SampleClass";
        List<SchemaField> schemaFields = new ArrayList<>();

        String generatedClass = ClassGenerator.generateClass(className, schemaFields);

        // Assert that the generated class contains the expected class name
        assertTrue(generatedClass.contains("public class " + className));

        // Assert that the generated class contains fields
        for (SchemaField field : schemaFields) {
            assertTrue(generatedClass.contains("public String " + field.fieldName + ";"));
        }

        // Test for a class with different field types
        List<SchemaField> fieldsWithDifferentTypes = new ArrayList<>();
        fieldsWithDifferentTypes.add(new SchemaField("field1", 0, 10));
        fieldsWithDifferentTypes.add(new SchemaField("field2", 11, 20));
        fieldsWithDifferentTypes.add(new SchemaField("field3", 21, 30));

        String generatedClassWithDifferentTypes = ClassGenerator.generateClass("DifferentClass", fieldsWithDifferentTypes);

        // Assert that the generated class contains fields with different types
        assertTrue(generatedClassWithDifferentTypes.contains("public String field1;"));
        assertTrue(generatedClassWithDifferentTypes.contains("public String field2;"));
        assertTrue(generatedClassWithDifferentTypes.contains("public String field3;"));

        // Test for an empty schemaFields list
        String emptyClass = ClassGenerator.generateClass("EmptyClass", new ArrayList<>());
        String expectedEmptyClassContent = "public class EmptyClass {";
        assertTrue(emptyClass.contains(expectedEmptyClassContent));
    }
}