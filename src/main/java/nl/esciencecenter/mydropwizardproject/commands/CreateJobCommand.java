package nl.esciencecenter.mydropwizardproject.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManager;
import nl.esciencecenter.mydropwizardproject.XenonManager;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class CreateJobCommand implements Command<CreateJobCommandParameters> {
    @Inject
    protected XenonManager xenonManager;
    @Inject
    protected ObjectMapper objectMapper;
    @Inject
    protected PathManager pathManager;

    @Override
    public void execute(CreateJobCommandParameters parameters) {
        UUID id = parameters.getId();
        File dir = new File(pathManager.getJobPath(id).toString());
        dir.mkdir();
        saveDataToFile(parameters.getAnnotatedData(), dir, parameters.getAnnotatedDataFileName());
        saveDataToFile(parameters.getConfig(), dir, parameters.getConfigFileName());
        saveDataToFile(parameters.getSchema(), dir, parameters.getSchemaFileName());
        String createXenonJob = xenonManager.createXenonJobAndReturnXenonJobId(id);
        createStatusFile(dir, parameters, createXenonJob);
    }

    private void createStatusFile(File dir, CreateJobCommandParameters parameters, String xenonJobId) {
        try {
            Path statusFilePath = pathManager.getJobStatusFilePath(parameters.getId());
            HashMap<String, String> statusMap = new HashMap<String, String>();
            statusMap.put("isDone", "false");
            statusMap.put("id", parameters.getId().toString());
            statusMap.put("xenonJobId", xenonJobId);
            objectMapper.writeValue(new File(statusFilePath.toString()), statusMap);
        } catch (IOException e) {
            throw new RuntimeException("Status of job (" + parameters.getId() + ") could not be retrieved.", e);
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
