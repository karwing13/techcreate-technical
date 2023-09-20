package org.example;

import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
class SchemaField {
    public String fieldName;
    public int startPosition;
    public int endPosition;

    public SchemaField(String fieldName, int startPosition, int endPosition) {
        this.fieldName = fieldName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}

class SchemaReader {
    public static List<SchemaField> readSchemaFile(String filePath) throws IOException {
        List<SchemaField> schemaFields = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String fieldName = parts[0].trim();
                    int startPosition = Integer.parseInt(parts[1].trim()) - 1;
                    int endPosition = Integer.parseInt(parts[2].trim());

                    SchemaField field = new SchemaField(fieldName, startPosition, endPosition);
                    schemaFields.add(field);
                }
            }
        }

        return schemaFields;
    }

    public static String extractClassName(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            if ((line = reader.readLine()) != null) {
                // Split the line using commas and get the first part as the class name
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    String className = parts[0].trim();
                    return className;
                }
            }
        }
        return null; // Return null if no class name is found
    }

    public static void main(String[] args) {
        try {
            String schemaFilePath = "schema";
            List<SchemaField> schemaFields = readSchemaFile(schemaFilePath);
            String className = extractClassName(schemaFilePath);

            if (className != null) {
                System.out.println("Class Name: " + className);

                // Generate the class
                String classContent = generateClass(className, schemaFields);
                System.out.println(classContent);

                // Generate the deserializer class
                String deserializerContent = generateDeserializer(className, schemaFields);
                System.out.println(deserializerContent);

                // Write to files
                writeToFile("src/main/java/org/example/" + className + ".java", classContent);
                writeToFile("src/main/java/org/example/" + className + "Deserialiser.java", deserializerContent);
            } else {
                System.out.println("Class Name not found in the schema.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateClass(String className, List<SchemaField> schemaFields) {
        StringBuilder sb = new StringBuilder();
        sb.append("package org.example;\n");
        sb.append("public class ").append(className).append(" {\n");

        for (SchemaField field : schemaFields) {
            sb.append("  public String ").append(field.fieldName).append(";\n");
        }

        sb.append("}");
        return sb.toString();
    }

    private static String generateDeserializer(String className, List<SchemaField> schemaFields) {
        StringBuilder sb = new StringBuilder();
        sb.append("package org.example;\n");
        sb.append("public class ").append(className).append("Deserialiser {\n");
        sb.append("  public ").append(className).append(" parse(String lineFeed) {\n");
        sb.append("    ").append(className).append(" record = new ").append(className).append("();\n");
        sb.append("    if (lineFeed != null) {\n");
        sb.append("      int length = lineFeed.length();\n");

        for (SchemaField field : schemaFields) {
            sb.append("      record.").append(field.fieldName).append(" = ");
            sb.append("extractSubstring(lineFeed, ").append(field.startPosition).append(", ");
            sb.append(field.endPosition).append(", length);\n");
        }

        sb.append("    } else {\n");
        sb.append("      // Handle cases where the input is null.\n");
        sb.append("      // Set default values or leave them as null as needed.\n");

        for (SchemaField field : schemaFields) {
            sb.append("      record.").append(field.fieldName).append(" = \"\";\n");
        }

        sb.append("    }\n");
        sb.append("    return record;\n");
        sb.append("  }\n");
        sb.append("\n");

        sb.append("  private String extractSubstring(String input, int startIndex, int endIndex, int length) {\n");
        sb.append("    if (startIndex >= length) {\n");
        sb.append("      return \"\";\n");
        sb.append("    }\n");
        sb.append("    int actualEndIndex = Math.min(endIndex, length);\n");
        sb.append("    return input.substring(startIndex, actualEndIndex).trim();\n");
        sb.append("  }\n");
        sb.append("}");

        return sb.toString();
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            outputStream.write(content.getBytes());
        }
    }
}