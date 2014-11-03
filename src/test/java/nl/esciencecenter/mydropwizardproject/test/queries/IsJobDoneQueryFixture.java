package nl.esciencecenter.mydropwizardproject.test.queries;

import nl.esciencecenter.mydropwizardproject.PathManager;
import nl.esciencecenter.mydropwizardproject.queries.IsJobDoneQuery;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IsJobDoneQueryFixture extends IsJobDoneQuery {
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
