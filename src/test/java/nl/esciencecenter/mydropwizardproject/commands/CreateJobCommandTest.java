package nl.esciencecenter.mydropwizardproject.commands;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManagerFixture;
import nl.esciencecenter.mydropwizardproject.XenonManagerFixture;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateJobCommandTest {
    private final UUID testId = UUID.fromString("a389ef8a-e026-4d1c-821b-2b99ca87b036");
    private final Path testPath = Paths.get("src/test/resources");
    private final String testFileName = "test123.txt";
    private final String testFileFullPath = testPath.resolve(testFileName).toString();
    private CreateJobCommandFixture command;
    private Path jobsDir;
    private XenonManagerFixture xenonManager;
    private CreateJobCommandParameters parameters;
    private PathManagerFixture pathManager;

    @Test
    public void execute_testId_dirWasCreated() {
        // Arrange

        // Act
        command.execute(parameters);

        // Assert
        File expectedOutputDir = new File(jobsDir + File.separator + testId.toString());
        assertTrue("doesn't exist", expectedOutputDir.exists());
        assertTrue("is not a directory", expectedOutputDir.isDirectory());
    }

    @Test
    public void execute_testId_statusFileWasCreated() {
        // Arrange

        // Act
        command.execute(parameters);

        // Assert
        File expectedOutputFile = new File(pathManager.getJobStatusFilePath(parameters.getId()).toString());
        assertTrue("doesn't exist", expectedOutputFile.exists());
    }

    @Test
    public void execute_testDataStream_dataFileWasSaved() throws FileNotFoundException {
        // Arrange
        FileInputStream testInputStream = new FileInputStream(testFileFullPath);
        parameters.setAnnotatedData(testInputStream);
        String annotatedDataFileName = "annotatedData.mat";
        parameters.setAnnotatedDataFileName(annotatedDataFileName);

        // Act
        command.execute(parameters);

        // Assert
        File expectedOutputFile = new File(jobsDir.resolve(testId.toString()).resolve(annotatedDataFileName).toString());
        assertTrue(expectedOutputFile.exists());
    }

    @Test
    public void execute_testDataStream_configFileWasSaved() throws FileNotFoundException {
        // Arrange
        FileInputStream testInputStream = new FileInputStream(testFileFullPath);
        parameters.setConfig(testInputStream);
        String configFileName = "config.properties";
        parameters.setConfigFileName(configFileName);

        // Act
        command.execute(parameters);

        // Assert
        File expectedOutputFile = new File(jobsDir.resolve(testId.toString()).resolve(configFileName).toString());
        assertTrue(expectedOutputFile.exists());
    }

    @Test
    public void execute_testDataStream_schemaFileWasSaved() throws FileNotFoundException {
        // Arrange
        FileInputStream testInputStream = new FileInputStream(testFileFullPath);
        parameters.setSchema(testInputStream);
        String schemaFileName = "schema.txt";
        parameters.setSchemaFileName(schemaFileName);

        // Act
        command.execute(parameters);

        // Assert
        File expectedOutputFile = new File(jobsDir + File.separator + testId.toString() + File.separator + schemaFileName);
        assertTrue(expectedOutputFile.exists());
    }

    @Before
    public void setUp() {
        parameters = new CreateJobCommandParameters();
        parameters.setId(testId);
        command = new CreateJobCommandFixture();
        pathManager = new PathManagerFixture();
        xenonManager = getXenonManager(pathManager);
        command.setXenonManager(xenonManager);
        command.setPathManager(pathManager);
        command.setObjectMapper(new ObjectMapper());
        jobsDir = pathManager.getJobsPath();
    }

    private XenonManagerFixture getXenonManager(PathManagerFixture pathManager) {
        XenonManagerFixture xenonManager = new XenonManagerFixture();
        xenonManager.setPathManager(pathManager);
        xenonManager.start();
        return xenonManager;
    }

    @After
    public void cleanUp() throws IOException {
        xenonManager.stop();
        FileUtils.deleteDirectory(new File(pathManager.getJobPath(testId).toString()));
    }
}
