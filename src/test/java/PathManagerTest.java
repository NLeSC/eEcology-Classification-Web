import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManager;

import org.junit.Before;
import org.junit.Test;

public class PathManagerTest {
    private PathManager pathManager;

    @Test
    public void getJobPath_correctPath() {
        // Arrange
        UUID jobId = UUID.fromString("c8c7e110-5155-11e4-916c-0800200c9a66");
        Path expected = Paths.get("jobs/" + jobId);

        // Act
        Path jobPath = pathManager.getJobPath(jobId);

        // Assert
        assertTrue(jobPath.equals(expected));
    }

    @Before
    public void setUp() {
        pathManager = new PathManager();
    }

}
