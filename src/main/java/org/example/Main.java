package org.example;
;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class Main {
    public static void main(String[]args) throws IOException {
        String schemaFilePath = "schema";
        List<SchemaField> schemaFields = SchemaReader.readSchemaFile(schemaFilePath);
        String className = SchemaReader.extractClassName(schemaFilePath);

        if (className != null) {
            System.out.println("Class Name: " + className);

            // Generate the class
            String classContent = ClassGenerator.generateClass(className, schemaFields);
            System.out.println(classContent);

            // Generate the deserializer class
            String deserializerContent = DeserialiserClassGenerator.generateDeserialiser(className, schemaFields);
            System.out.println(deserializerContent);

            // Write to files
            writeToFile("src/main/java/org/example/" + className + ".java", classContent);
            writeToFile("src/main/java/org/example/" + className + "Deserialiser.java", deserializerContent);
        } else {
            System.out.println("Class Name not found in the schema.");
        }
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            outputStream.write(content.getBytes());
        }
    }
}