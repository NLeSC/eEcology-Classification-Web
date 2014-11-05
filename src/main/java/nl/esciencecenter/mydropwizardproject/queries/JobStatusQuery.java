package nl.esciencecenter.mydropwizardproject.queries;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManager;
import nl.esciencecenter.mydropwizardproject.domain.JobStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class JobStatusQuery implements Query<UUID, JobStatus> {
    @Inject
    protected ObjectMapper objectMapper;
    @Inject
    protected PathManager pathManager;

    @Override
    public JobStatus run(UUID jobId) {
        JobStatus jobStatus = getStatus(jobId);
        return jobStatus;
    }

    private JobStatus getStatus(UUID jobId) {
        JobStatus map = null;
        try {
            map = objectMapper.readValue(new File(getStatusFileLocation(jobId)), new TypeReference<JobStatus>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Status of job (" + jobId + ") could not be retrieved.", e);
        }
        return map;
    }

    private String getStatusFileLocation(UUID jobId) {
        return pathManager.getJobStatusFilePath(jobId).toString();
    }
}
