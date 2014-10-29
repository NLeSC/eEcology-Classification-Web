package nl.esciencecenter.mydropwizardproject;

import java.nio.file.Path;

public class PathManagerFixture extends PathManager {
    public void setJobsPath(Path jobsPath) {
        this.jobsPath = jobsPath;
    }
}
