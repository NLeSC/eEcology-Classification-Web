import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManagerFixture;

import org.junit.Before;
import org.junit.Test;

public class PathManagerTest {
    private PathManagerFixture pathManager;
    private final String jobs = "jobs";
    private final Path jobsPath = Paths.get(jobs);
    private final Path classificationToolPath = Paths.get("classificationtool");

    @Test
    public void getJobPath_correctPath() {
        // Arrange
        UUID jobId = UUID.fromString("c8c7e110-5155-11e4-916c-0800200c9a66");
        Path expected = jobsPath.resolve(jobId.toString());

        // Act
        Path jobPath = pathManager.getJobPath(jobId);

        // Assert
        assertTrue(jobPath.equals(expected));
    }

    @Test
    public void getJobPathRelativeToClassifierTool() {
        // Arrange
        UUID jobId = UUID.fromString("c8c7e110-5155-11e4-916c-0800200c9a66");
        Path expected = classificationToolPath.relativize(Paths.get("")).resolve(jobs).resolve(jobId.toString());

        // Act
        Path jobPath = pathManager.getJobPathForClassificationTool(jobId);

        // Assert
        assertTrue(expected.equals(jobPath));
    }

    @Before
    public void setUp() {
        pathManager = new PathManagerFixture();
        pathManager.setClassificationToolPath(classificationToolPath);
        pathManager.setJobsPath(jobsPath);
    }

}
