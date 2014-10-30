package nl.esciencecenter.mydropwizardproject.resources;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.esciencecenter.mydropwizardproject.core.Saying;
import nl.esciencecenter.mydropwizardproject.queries.IsJobDoneQuery;
import nl.esciencecenter.mydropwizardproject.queries.IsJobDoneQueryParameters;
import nl.esciencecenter.mydropwizardproject.queries.IsJobDoneQueryResult;

import com.google.inject.Inject;

@Path("/jobstatuses")
public class JobStatusResource {
    @Inject
    IsJobDoneQuery isJobDoneQuery;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Saying getJobStatus(@QueryParam("id") final String jobId) {
        IsJobDoneQueryParameters parameters = new IsJobDoneQueryParameters();
        parameters.setJobId(UUID.fromString(jobId));
        IsJobDoneQueryResult queryResult = isJobDoneQuery.run(parameters);
        return new Saying(jobId, queryResult.isDone());
    }
}
