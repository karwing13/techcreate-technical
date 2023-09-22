package org.example;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.List;

public class ClassGenerator {
    public static String generateClass(String className, List<SchemaField> schemaFields) {
        String[] fieldNames = new String[schemaFields.size()];
        for (int i = 0; i < schemaFields.size(); i++) {
            SchemaField field = schemaFields.get(i);
            fieldNames[i] = field.fieldName;
        }

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty("resource.loader", "class");
        ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.init();

        VelocityContext context = new VelocityContext();
        context.put("packageName", "org.example");
        context.put("className", className);
        context.put("schemaFields", fieldNames);

        Template template = ve.getTemplate("class_template.vm");

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
}