package nl.esciencecenter.mydropwizardproject.queries;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class IsJobDoneQuery implements Query<IsJobDoneQueryParameters, IsJobDoneQueryResult> {
    @Inject
    protected ObjectMapper objectMapper;
    @Inject
    protected PathManager pathManager;

    @Override
    public IsJobDoneQueryResult run(IsJobDoneQueryParameters parameters) {
        Map<String, String> map = getStatusMap(parameters);
        boolean isDone = Boolean.parseBoolean(map.get("isDone"));
        IsJobDoneQueryResult result = new IsJobDoneQueryResult();
        result.setDone(isDone);
        return result;
    }

    private Map<String, String> getStatusMap(IsJobDoneQueryParameters parameters) {
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(new File(getStatusFileLocation(parameters.getJobId())),
                    new TypeReference<HashMap<String, String>>() {
                    });
        } catch (IOException e) {
            throw new RuntimeException("Status of job (" + parameters.getJobId() + ") could not be retrieved.", e);
        }
        return map;
    }

    private String getStatusFileLocation(UUID jobId) {
        return pathManager.getJobStatusFilePath(jobId).toString();
    }
}
