package nl.esciencecenter.mydropwizardproject.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import nl.esciencecenter.xenon.Xenon;
import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.XenonFactory;
import nl.esciencecenter.xenon.jobs.Job;
import nl.esciencecenter.xenon.jobs.JobDescription;
import nl.esciencecenter.xenon.jobs.Scheduler;

import org.apache.commons.io.FileUtils;

public class CreateJobCommand implements Command {

    private final UUID id;
    private InputStream annotatedData;
    private String annotatedDataFileName;
    private InputStream config;
    private String configFileName;
    private InputStream schema;
    private String schemaFileName;

    public CreateJobCommand(UUID id) {
        this.id = id;
    }

    public void setSchema(InputStream schema) {
        this.schema = schema;
    }

    public void setSchemaFileName(String schemaFileName) {
        this.schemaFileName = schemaFileName;
    }

    public void setAnnotatedData(InputStream annotatedData) {
        this.annotatedData = annotatedData;
    }

    public void setAnnotatedDataFileName(String annotatedDataFileName) {
        this.annotatedDataFileName = annotatedDataFileName;
    }

    public void setConfig(InputStream config) {
        this.config = config;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

    @Override
    public void execute() {
        File dir = new File("jobs" + File.separator + id.toString());
        dir.mkdir();
        saveDataToFile(annotatedData, dir, annotatedDataFileName);
        saveDataToFile(config, dir, configFileName);
        saveDataToFile(schema, dir, schemaFileName);
        createXenonJob(id);
    }

    private void createXenonJob(UUID jobId) {
        JobDescription jobDescription = new JobDescription();
        jobDescription.setExecutable("java");
        String jobDirectory = "../jobs/" + jobId.toString();
        jobDescription.setArguments("-jar", "classificationtool.jar", jobDirectory);
        jobDescription.setWorkingDirectory("classificationtool");
        jobDescription.setStdout(jobDirectory + "/stdout.txt");
        jobDescription.setStderr(jobDirectory + "/stderr.txt");
        Xenon xenon;
        Scheduler scheduler;
        try {
            //start xenon
            xenon = XenonFactory.newXenon(null);
            scheduler = xenon.jobs().newScheduler("local", null, null, null);

            //add job
            Job job = xenon.jobs().submitJob(scheduler, jobDescription);
            xenon.jobs().waitUntilDone(job, 30000);

            //close xenon
            xenon.jobs().close(scheduler);
            XenonFactory.endXenon(xenon);
        } catch (XenonException e) {
            throw new RuntimeException("Xenon threw an exception.", e);
        }
    }

    private void saveDataToFile(InputStream sourceData, File dir, String targetFileName) {
        if (sourceData != null) {
            String filePath = "";
            try {
                filePath = dir.getCanonicalPath() + File.separator + targetFileName;
            } catch (IOException e) {
                throwRuntimeException(e);
            }
            File file = new File(filePath);
            try {
                FileUtils.copyInputStreamToFile(sourceData, file);
            } catch (IOException e) {
                throwRuntimeException(e);
            }
        }
    }

    private void throwRuntimeException(IOException e) {
        throw new RuntimeException("Could not save the input data.", e);
    }

}
