package org.example;

import java.util.List;

public class DeserialiserClassGenerator {
    public static String generateDeserialiser(String className, List<SchemaField> schemaFields) {
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
}