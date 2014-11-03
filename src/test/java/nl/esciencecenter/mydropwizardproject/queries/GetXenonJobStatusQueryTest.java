package nl.esciencecenter.mydropwizardproject.queries;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManagerFixture;
import nl.esciencecenter.mydropwizardproject.XenonManagerFixture;
import nl.esciencecenter.mydropwizardproject.test.queries.GetXenonJobStatusQueryFixture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetXenonJobStatusQueryTest {

    private GetXenonJobStatusQueryFixture getXenonJobStatusQuery;
    private GetXenonJobStatusQueryParameters parameters;
    private XenonManagerFixture xenonManagerFixture;
    private final UUID fastJobId = UUID.fromString("7b1dc80d-a18f-4cf8-bc7d-84140a1e201e"); // A fast job where nothing has to be executed

    @Test
    public void jobIsFinished_returnIsDoneIsTrue() throws InterruptedException {
        // Arrange
        String xenonJobId = xenonManagerFixture.createXenonJobAndReturnXenonJobId(fastJobId);
        parameters = new GetXenonJobStatusQueryParameters();
        parameters.setXenonJobId(xenonJobId);
        Thread.sleep(100); // Wait to be sure that the job was finished

        // Act
        GetXenonJobStatusQueryResult result = getXenonJobStatusQuery.run(parameters);

        // Assert
        assertEquals(true, result.isDone());
    }

    @Test
    public void jobNotSubmitted_returnIsDoneIsFalse() {
        // Arrange
        String xenonJobId = "nonExistentId";
        parameters = new GetXenonJobStatusQueryParameters();
        parameters.setXenonJobId(xenonJobId);

        // Act
        GetXenonJobStatusQueryResult result = getXenonJobStatusQuery.run(parameters);

        // Assert
        assertEquals(false, result.isDone());
    }

    @Before
    public void setUp() {
        getXenonJobStatusQuery = new GetXenonJobStatusQueryFixture();
        xenonManagerFixture = new XenonManagerFixture();
        xenonManagerFixture.setPathManager(new PathManagerFixture());
        xenonManagerFixture.start();
        getXenonJobStatusQuery.setXenonManager(xenonManagerFixture);
    }

    @After
    public void cleanUp() {
        xenonManagerFixture.stop();
    }
}
