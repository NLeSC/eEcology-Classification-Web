package nl.esciencecenter.mydropwizardproject.queries;

import nl.esciencecenter.mydropwizardproject.PathManager;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IsJobDoneQueryFixture extends IsJobDoneQuery {
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
