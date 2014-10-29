package nl.esciencecenter.mydropwizardproject.queries;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManagerFixture;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IsJobDoneQueryTest {
    private final String testJobDir = "src" + File.separatorChar + "test" + File.separatorChar + "java" + File.separatorChar
            + "resources" + File.separatorChar + "jobs" + File.separatorChar;
    private final UUID unfinishedJobId = UUID.fromString("36efb35c-c99c-4c3a-8f2d-0bf8354ccad4");
    private final UUID finishedJobId = UUID.fromString("c8c7e110-5155-11e4-916c-0800200c9a66");
    private IsJobDoneQueryFixture isJobDoneQuery;

    @Test
    public void unfinishedJobId_isDoneIsFalse() throws IOException {
        // Arrange
        IsJobDoneQueryParameters parameters = new IsJobDoneQueryParameters();
        parameters.setJobId(unfinishedJobId);

        // Act
        IsJobDoneQueryResult result = isJobDoneQuery.run(parameters);

        // Assert
        assertEquals(false, result.isDone());
    }

    @Test
    public void finishedJobId_isDoneIsTrue() throws IOException {
        // Arrange
        IsJobDoneQueryParameters parameters = new IsJobDoneQueryParameters();
        parameters.setJobId(finishedJobId);

        // Act
        IsJobDoneQueryResult result = isJobDoneQuery.run(parameters);

        // Assert
        assertEquals(true, result.isDone());
    }

    @Before
    public void setUp() {
        isJobDoneQuery = new IsJobDoneQueryFixture();
        isJobDoneQuery.setObjectMapper(new ObjectMapper());
        PathManagerFixture pathManager = new PathManagerFixture();
        pathManager.setJobsPath(Paths.get(testJobDir));
        isJobDoneQuery.setPathManager(pathManager);
    }
}
