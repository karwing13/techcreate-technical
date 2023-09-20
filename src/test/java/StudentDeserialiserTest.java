import static org.junit.Assert.*;

import org.example.Student;
import org.example.StudentDeserialiser;
import org.junit.Before;
import org.junit.Test;

public class StudentDeserialiserTest {

    private StudentDeserialiser deserialiser;

    @Before
    public void setUp() {
        deserialiser = new StudentDeserialiser();
    }

    @Test
    public void testParseValidInput() {
        String input = "John                Doe                 Junior    A";
        Student student = deserialiser.parse(input);

        assertEquals("John", student.firstName);
        assertEquals("Doe", student.lastName);
        assertEquals("Junior", student.level);
        assertEquals("A", student.studentClass);
    }

    @Test
    public void testParseEmptyInput() {
        String input = "                    ";
        Student student = deserialiser.parse(input);

        assertEquals("", student.firstName);
        assertEquals("", student.lastName);
        assertEquals("", student.level);
        assertEquals("", student.studentClass);
    }

    @Test
    public void testParseShortInput() {
        String input = "John                Doe                 Junior";
        Student student = deserialiser.parse(input);

        assertEquals("John", student.firstName);
        assertEquals("Doe", student.lastName);
        assertEquals("Junior", student.level);
        assertEquals("", student.studentClass);
    }

    @Test
    public void testParseNullInput() {
        String input = null;
        Student student = deserialiser.parse(input);

        assertEquals("", student.firstName);
        assertEquals("", student.lastName);
        assertEquals("", student.level);
        assertEquals("", student.studentClass);
    }

    @Test
    public void testParseInvalidInput() {
        String input = "InvalidInput";
        Student student = deserialiser.parse(input);

        // Since the input does not match the expected format, all fields should be empty strings.
        assertEquals("InvalidInput", student.firstName);
        assertEquals("", student.lastName);
        assertEquals("", student.level);
        assertEquals("", student.studentClass);
    }

    @Test
    public void testParseValidInputWithExtraFields() {
        String input = "John                Doe                 Junior    A         ExtraData";
        Student student = deserialiser.parse(input);

        assertEquals("John", student.firstName);
        assertEquals("Doe", student.lastName);
        assertEquals("Junior", student.level);
        assertEquals("A", student.studentClass);
    }
}
