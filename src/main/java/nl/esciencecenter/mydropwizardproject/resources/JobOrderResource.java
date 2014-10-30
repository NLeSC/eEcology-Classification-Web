package nl.esciencecenter.mydropwizardproject.resources;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import nl.esciencecenter.mydropwizardproject.commands.CreateJobCommand;
import nl.esciencecenter.mydropwizardproject.commands.CreateJobCommandParameters;

import com.google.inject.Inject;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/jobs")
public class JobOrderResource {
    @Inject
    private CreateJobCommand command;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("input1") String input1, @FormDataParam("config") InputStream config,
            @FormDataParam("config") FormDataContentDisposition configDisposition,
            @FormDataParam("annotateddata") InputStream annotatedData,
            @FormDataParam("annotateddata") FormDataContentDisposition annotatedDataDisposition,
            @FormDataParam("schema") InputStream schema, @FormDataParam("schema") FormDataContentDisposition schemaDisposition) {
        UUID jobId = UUID.randomUUID();
        CreateJobCommandParameters parameters = createCommandParameters(jobId, config, configDisposition, annotatedData,
                annotatedDataDisposition, schema, schemaDisposition);
        command.execute(parameters);
        URI location = UriBuilder.fromPath("../assets/poll.html").queryParam("id", jobId).build();
        return Response.seeOther(location).build();
    }

    private CreateJobCommandParameters
            createCommandParameters(UUID jobId, InputStream config, FormDataContentDisposition configDisposition,
                    InputStream annotatedData, FormDataContentDisposition annotatedDataDisposition, InputStream schema,
                    FormDataContentDisposition schemaDisposition) {
        CreateJobCommandParameters parameters = new CreateJobCommandParameters();
        parameters.setId(jobId);
        parameters.setAnnotatedData(annotatedData);
        parameters.setAnnotatedDataFileName(annotatedDataDisposition.getFileName());
        parameters.setConfig(config);
        parameters.setConfigFileName(configDisposition.getFileName());
        parameters.setSchema(schema);
        parameters.setSchemaFileName(schemaDisposition.getFileName());
        return parameters;
    }

}