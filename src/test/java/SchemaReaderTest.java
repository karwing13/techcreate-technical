import org.example.SchemaField;
import org.example.SchemaReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.List;

public class SchemaReaderTest {
    @Test
    public void testReadSchemaFile() throws IOException {
        // Provide a sample schema file for testing
        String filePath = "schema_UT";

        List<SchemaField> schemaFields = SchemaReader.readSchemaFile(filePath);

        // Assert the expected number of fields
        assertEquals(3, schemaFields.size());

        // Test individual field properties
        SchemaField field1 = schemaFields.get(0);
        assertEquals("firstName", field1.fieldName);
        assertEquals(0, field1.startPosition);
        assertEquals(20, field1.endPosition);

        SchemaField field2 = schemaFields.get(1);
        assertEquals("lastName", field2.fieldName);
        assertEquals(20, field2.startPosition);
        assertEquals(40, field2.endPosition);

        SchemaField field3 = schemaFields.get(2);
        assertEquals("level", field3.fieldName);
        assertEquals(40, field3.startPosition);
        assertEquals(50, field3.endPosition);
    }
}
