package nl.esciencecenter.mydropwizardproject.resources;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.esciencecenter.mydropwizardproject.commands.UpdateJobStatusCommand;
import nl.esciencecenter.mydropwizardproject.core.Saying;
import nl.esciencecenter.mydropwizardproject.domain.JobStatus;
import nl.esciencecenter.mydropwizardproject.queries.JobStatusQuery;
import nl.esciencecenter.mydropwizardproject.queries.XenonJobStatusQuery;

import com.google.inject.Inject;

@Path("/jobstatuses")
public class JobStatusResource {
    @Inject
    private JobStatusQuery jobStatusQuery;
    @Inject
    private XenonJobStatusQuery getXenonJobStatusQuery;
    @Inject
    private UpdateJobStatusCommand updateJobStatusCommand;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Saying getJobStatus(@QueryParam("id") final String jobIdStr) {
        UUID jobId = UUID.fromString(jobIdStr);
        updateJobStatusCommand.execute(jobId);
        JobStatus jobStatus = jobStatusQuery.run(jobId);
        return new Saying(jobIdStr, jobStatus.isDone());
    }
}
