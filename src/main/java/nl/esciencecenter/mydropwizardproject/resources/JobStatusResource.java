package nl.esciencecenter.mydropwizardproject.resources;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.esciencecenter.mydropwizardproject.core.Saying;
import nl.esciencecenter.mydropwizardproject.queries.GetXenonJobStatusQuery;
import nl.esciencecenter.mydropwizardproject.queries.GetXenonJobStatusQueryParameters;
import nl.esciencecenter.mydropwizardproject.queries.GetXenonJobStatusQueryResult;
import nl.esciencecenter.mydropwizardproject.queries.IsJobDoneQuery;
import nl.esciencecenter.mydropwizardproject.queries.IsJobDoneQueryParameters;
import nl.esciencecenter.mydropwizardproject.queries.IsJobDoneQueryResult;

import com.google.inject.Inject;

@Path("/jobstatuses")
public class JobStatusResource {
    @Inject
    private IsJobDoneQuery isJobDoneQuery;
    @Inject
    private GetXenonJobStatusQuery getXenonJobStatusQuery;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Saying getJobStatus(@QueryParam("id") final String jobId) {
        IsJobDoneQueryResult queryResult = isJobDoneQuery.run(getIsJobDoneParameters(jobId));
        boolean isDone = queryResult.isDone();
        if (isDone == false) {
            GetXenonJobStatusQueryParameters parameters = new GetXenonJobStatusQueryParameters();
            parameters.setXenonJobId(jobId);
            GetXenonJobStatusQueryResult xenonStatusResult = getXenonJobStatusQuery.run(parameters);
            isDone = xenonStatusResult.isDone();
        }

        return new Saying(jobId, isDone);
    }

    private IsJobDoneQueryParameters getIsJobDoneParameters(final String jobId) {
        IsJobDoneQueryParameters parameters = new IsJobDoneQueryParameters();
        parameters.setJobId(UUID.fromString(jobId));
        return parameters;
    }
}
