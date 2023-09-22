package org.example;

public class SchemaField {
    public String fieldName;
    public int startPosition;
    public int endPosition;

    public SchemaField(String fieldName, int startPosition, int endPosition) {
        this.fieldName = fieldName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}
