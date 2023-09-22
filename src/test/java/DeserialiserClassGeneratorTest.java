import org.example.DeserialiserClassGenerator;
import org.example.SchemaField;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DeserialiserClassGeneratorTest {
    @Test
    public void testGenerateDeserialiser() {
        String className = "SampleClass";
        List<SchemaField> schemaFields = new ArrayList<>();

        String generatedDeserialiser = DeserialiserClassGenerator.generateDeserialiser(className, schemaFields);

        // Assert the expected deserialiser content or structure
        String expectedDeserialiserContent = "public class SampleClassDeserialiser {";
        assertTrue(generatedDeserialiser.contains(expectedDeserialiserContent));

        // Test for generating a deserialiser with fields and extraction logic
        List<SchemaField> fieldsWithExtractionLogic = new ArrayList<>();
        fieldsWithExtractionLogic.add(new SchemaField("field1", 0, 10));
        fieldsWithExtractionLogic.add(new SchemaField("field2", 11, 20));
        fieldsWithExtractionLogic.add(new SchemaField("field3", 21, 30));

        String generatedDeserialiserWithExtraction = DeserialiserClassGenerator.generateDeserialiser("DifferentClass", fieldsWithExtractionLogic);

        // Assert that the generated deserialiser contains extraction logic for all fields
        assertTrue(generatedDeserialiserWithExtraction.contains("record.field1 = extractSubstring(lineFeed, 0, 10, length);"));
        assertTrue(generatedDeserialiserWithExtraction.contains("record.field2 = extractSubstring(lineFeed, 11, 20, length);"));
        assertTrue(generatedDeserialiserWithExtraction.contains("record.field3 = extractSubstring(lineFeed, 21, 30, length);"));
    }
}
