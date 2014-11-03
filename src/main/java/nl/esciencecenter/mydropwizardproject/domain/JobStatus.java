package nl.esciencecenter.mydropwizardproject.domain;

import java.util.UUID;

public class JobStatus {
    private UUID jobId;
    private String xenonJobId;
    private boolean isDone;

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public String getXenonJobId() {
        return xenonJobId;
    }

    public void setXenonJobId(String xenonJobId) {
        this.xenonJobId = xenonJobId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
}
