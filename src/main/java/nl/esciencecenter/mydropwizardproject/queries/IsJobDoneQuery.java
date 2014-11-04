package nl.esciencecenter.mydropwizardproject.queries;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManager;
import nl.esciencecenter.mydropwizardproject.domain.JobStatus;

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
        JobStatus jobStatus = getStatus(parameters);
        IsJobDoneQueryResult result = new IsJobDoneQueryResult();
        boolean isDone = jobStatus.isDone();
        result.setDone(isDone);
        return result;
    }

    private JobStatus getStatus(IsJobDoneQueryParameters parameters) {
        JobStatus map = null;
        try {
            map = objectMapper.readValue(new File(getStatusFileLocation(parameters.getJobId())), new TypeReference<JobStatus>() {
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
