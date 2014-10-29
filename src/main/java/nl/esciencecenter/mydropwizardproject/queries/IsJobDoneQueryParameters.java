package nl.esciencecenter.mydropwizardproject.queries;

import java.util.UUID;

public class IsJobDoneQueryParameters implements QueryParameters {

    private UUID jobId;

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public UUID getJobId() {
        return jobId;
    }

}
