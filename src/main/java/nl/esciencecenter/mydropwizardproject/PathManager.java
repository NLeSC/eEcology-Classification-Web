package nl.esciencecenter.mydropwizardproject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class PathManager {

    protected Path jobsPath = Paths.get("jobs");
    private final String statusFileName = "status.json";

    public Path getJobPath(UUID jobId) {
        return jobsPath.resolve(jobId.toString());
    }

    public Path getJobStatusFilePath(UUID jobId) {
        return getJobPath(jobId).resolve(statusFileName);
    }

}
