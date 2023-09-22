package org.example;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeserialiserClassGenerator {
    public static String generateDeserialiser(String className, List<SchemaField> schemaFields) {
        List<Map<String, Object>> fieldDataList = new ArrayList<>();
        for (SchemaField field : schemaFields) {
            Map<String, Object> fieldData = new HashMap<>();
            fieldData.put("fieldName", field.fieldName);
            fieldData.put("startPosition", field.startPosition);
            fieldData.put("endPosition", field.endPosition);
            fieldDataList.add(fieldData);
        }

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty("resource.loader", "class");
        ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.init();

        VelocityContext context = new VelocityContext();
        context.put("className", className);
        context.put("fieldDataList", fieldDataList);

        Template template = ve.getTemplate("deserializer_template.vm");

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
}