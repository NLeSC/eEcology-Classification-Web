package nl.esciencecenter.mydropwizardproject.commands;

import nl.esciencecenter.mydropwizardproject.PathManager;
import nl.esciencecenter.mydropwizardproject.XenonManager;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateJobCommandFixture extends CreateJobCommand {
    public void setXenonManager(XenonManager xenonManager) {
        this.xenonManager = xenonManager;
    }

    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
