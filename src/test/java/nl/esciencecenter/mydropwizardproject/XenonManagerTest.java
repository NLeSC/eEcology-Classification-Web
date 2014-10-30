package nl.esciencecenter.mydropwizardproject;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class XenonManagerTest {

    private XenonManagerFixture xenonManager;
    private final UUID testId = UUID.fromString("ff0de020-3475-410d-81e2-f32c69bee6c1");

    @Test
    public void canStartStopXenon() {
        // Arrange

        // Act
        xenonManager.start();
        xenonManager.stop();

        // Assert
    }

    @Test
    public void createXenonJob() {
        // Arrange
        xenonManager.start();

        // Act
        xenonManager.createXenonJob(testId);
        xenonManager.stop();

        // Assert
    }

    @Before
    public void setUp() {
        xenonManager = new XenonManagerFixture();
        PathManagerFixture pathManager = new PathManagerFixture();
        xenonManager.setPathManager(pathManager);
    }
}
