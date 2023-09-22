package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchemaReader {
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
}