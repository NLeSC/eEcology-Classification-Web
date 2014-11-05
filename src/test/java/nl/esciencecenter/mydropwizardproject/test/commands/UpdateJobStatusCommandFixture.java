package nl.esciencecenter.mydropwizardproject.test.commands;

import nl.esciencecenter.mydropwizardproject.PathManager;
import nl.esciencecenter.mydropwizardproject.commands.UpdateJobStatusCommand;
import nl.esciencecenter.mydropwizardproject.queries.JobStatusQuery;
import nl.esciencecenter.mydropwizardproject.queries.XenonJobStatusQuery;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateJobStatusCommandFixture extends UpdateJobStatusCommand {
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setJobStatusQuery(JobStatusQuery jobStatusQuery) {
        this.jobStatusQuery = jobStatusQuery;
    }

    public void setXenonJobStatusQuery(XenonJobStatusQuery xenonJobStatusQuery) {
        this.xenonJobStatusQuery = xenonJobStatusQuery;
    }
}
