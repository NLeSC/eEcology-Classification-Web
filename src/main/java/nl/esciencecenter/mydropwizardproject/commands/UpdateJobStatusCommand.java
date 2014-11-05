package nl.esciencecenter.mydropwizardproject.commands;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManager;
import nl.esciencecenter.mydropwizardproject.domain.JobStatus;
import nl.esciencecenter.mydropwizardproject.queries.JobStatusQuery;
import nl.esciencecenter.mydropwizardproject.queries.XenonJobStatusQuery;
import nl.esciencecenter.mydropwizardproject.queries.XenonJobStatusQueryParameters;
import nl.esciencecenter.mydropwizardproject.queries.XenonJobStatusQueryResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class UpdateJobStatusCommand implements Command<UUID> {
    @Inject
    protected ObjectMapper objectMapper;
    @Inject
    protected PathManager pathManager;
    @Inject
    protected JobStatusQuery jobStatusQuery;
    @Inject
    protected XenonJobStatusQuery xenonJobStatusQuery;

    @Override
    public void execute(UUID jobId) {
        JobStatus jobStatus = jobStatusQuery.run(jobId);
        if (jobStatus.isDone()) {
            return; // nothing to update, this is the final status
        }

        jobStatus.setDone(isXenonJobDone(jobStatus.getXenonJobId()));
        saveJobStatus(jobId, jobStatus);
    }

    private boolean isXenonJobDone(String xenonJobId) {
        XenonJobStatusQueryParameters parameters = new XenonJobStatusQueryParameters();
        parameters.setXenonJobId(xenonJobId);
        XenonJobStatusQueryResult xenonResult = xenonJobStatusQuery.run(parameters);
        return xenonResult.isDone();
    }

    private void saveJobStatus(UUID jobId, JobStatus jobStatus) {
        try {
            objectMapper.writeValue(new File(pathManager.getJobStatusFilePath(jobId).toString()), jobStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
