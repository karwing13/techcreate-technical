package org.example;

import java.util.List;

public class ClassGenerator {
    public static String generateClass(String className, List<SchemaField> schemaFields) {
        StringBuilder sb = new StringBuilder();
        sb.append("package org.example;\n");
        sb.append("public class ").append(className).append(" {\n");

        for (SchemaField field : schemaFields) {
            sb.append("  public String ").append(field.fieldName).append(";\n");
        }

        sb.append("}");
        return sb.toString();
    }
}