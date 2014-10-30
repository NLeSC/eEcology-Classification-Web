package nl.esciencecenter.mydropwizardproject.commands;

import java.io.InputStream;
import java.util.UUID;

public class CreateJobCommandParameters implements CommandParameters {
    private UUID id;
    private InputStream annotatedData;
    private String annotatedDataFileName;
    private InputStream config;
    private String configFileName;
    private InputStream schema;
    private String schemaFileName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public InputStream getAnnotatedData() {
        return annotatedData;
    }

    public void setAnnotatedData(InputStream annotatedData) {
        this.annotatedData = annotatedData;
    }

    public String getAnnotatedDataFileName() {
        return annotatedDataFileName;
    }

    public void setAnnotatedDataFileName(String annotatedDataFileName) {
        this.annotatedDataFileName = annotatedDataFileName;
    }

    public InputStream getConfig() {
        return config;
    }

    public void setConfig(InputStream config) {
        this.config = config;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

    public InputStream getSchema() {
        return schema;
    }

    public void setSchema(InputStream schema) {
        this.schema = schema;
    }

    public String getSchemaFileName() {
        return schemaFileName;
    }

    public void setSchemaFileName(String schemaFileName) {
        this.schemaFileName = schemaFileName;
    }

}
