package nl.esciencecenter.mydropwizardproject;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathManagerFixture extends PathManager {
    public PathManagerFixture() {
        Path testPath = Paths.get("src/test/resources/jobs");
        setJobsPath(testPath);
    }

    public void setJobsPath(Path jobsPath) {
        this.jobsPath = jobsPath;
    }

    public Path getJobsPath() {
        return jobsPath;
    }

    public void setClassificationToolPath(Path classificationToolPath) {
        this.classificationToolPath = classificationToolPath;
    }

}
