package nl.esciencecenter.mydropwizardproject.resources;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import nl.esciencecenter.mydropwizardproject.commands.CreateJobCommand;
import nl.esciencecenter.mydropwizardproject.core.Saying;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/jobs")
public class HelloWorldResource {
    private int count = 0;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("input1") String input1, @FormDataParam("config") InputStream config,
            @FormDataParam("config") FormDataContentDisposition configDisposition,
            @FormDataParam("annotateddata") InputStream annotatedData,
            @FormDataParam("annotateddata") FormDataContentDisposition annotatedDataDisposition,
            @FormDataParam("schema") InputStream schema, @FormDataParam("schema") FormDataContentDisposition schemaDisposition) {
        UUID jobId = UUID.randomUUID();
        CreateJobCommand command = new CreateJobCommand(jobId);
        command.setAnnotatedData(annotatedData);
        command.setAnnotatedDataFileName(annotatedDataDisposition.getFileName());
        command.setConfig(config);
        command.setConfigFileName(configDisposition.getFileName());
        command.setSchema(schema);
        command.setSchemaFileName(schemaDisposition.getFileName());
        command.execute();
        URI location = UriBuilder.fromPath("../../jobs").queryParam("id", jobId).build();
        return Response.seeOther(location).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Saying getJobStatus(@QueryParam(value = "id") final String jobId) {
        count++;
        count %= 10;
        boolean jobIsReady = count == 9;
        return new Saying(jobId, jobIsReady);
    }
}