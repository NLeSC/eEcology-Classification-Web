package nl.esciencecenter.mydropwizardproject.commands;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateJobCommandTest {
    private final UUID testId = UUID.fromString("a389ef8a-e026-4d1c-821b-2b99ca87b036");
    private final String testPath = "src" + File.separator + "test" + File.separator + "java" + File.separator + "resources"
            + File.separator;
    private final String testFileName = "test123.txt";
    private final String testFileFullPath = testPath + testFileName;
    private CreateJobCommand command;
    private final List<File> createdFiles = new LinkedList<File>();
    private final List<File> createdDirectories = new LinkedList<File>();
    String jobsDir = "jobs";

    @Test
    public void execute_testId_dirWasCreated() {
        // Arrange

        // Act
        command.execute();

        // Assert
        File expectedOutputDir = new File(jobsDir + File.separator + testId.toString());
        createdDirectories.add(expectedOutputDir);
        assertTrue("doesn't exist", expectedOutputDir.exists());
        assertTrue("is not a directory", expectedOutputDir.isDirectory());
    }

    @Test
    public void execute_testDataStream_dataFileWasSaved() throws FileNotFoundException {
        // Arrange
        FileInputStream testInputStream = new FileInputStream(testFileFullPath);
        command.setAnnotatedData(testInputStream);
        String annotatedDataFileName = "annotatedData.mat";
        command.setAnnotatedDataFileName(annotatedDataFileName);

        // Act
        command.execute();

        // Assert
        File expectedOutputFile = new File(jobsDir + File.separator + testId.toString() + File.separator + annotatedDataFileName);
        createdFiles.add(expectedOutputFile);
        assertTrue(expectedOutputFile.exists());
    }

    @Test
    public void execute_testDataStream_configFileWasSaved() throws FileNotFoundException {
        // Arrange
        FileInputStream testInputStream = new FileInputStream(testFileFullPath);
        command.setConfig(testInputStream);
        String configFileName = "config.properties";
        command.setConfigFileName(configFileName);

        // Act
        command.execute();

        // Assert
        File expectedOutputFile = new File(jobsDir + File.separator + testId.toString() + File.separator + configFileName);
        createdFiles.add(expectedOutputFile);
        assertTrue(expectedOutputFile.exists());
    }

    @Test
    public void execute_testDataStream_schemaFileWasSaved() throws FileNotFoundException {
        // Arrange
        FileInputStream testInputStream = new FileInputStream(testFileFullPath);
        command.setSchema(testInputStream);
        String schemaFileName = "schema.txt";
        command.setSchemaFileName(schemaFileName);

        // Act
        command.execute();

        // Assert
        File expectedOutputFile = new File(jobsDir + File.separator + testId.toString() + File.separator + schemaFileName);
        createdFiles.add(expectedOutputFile);
        assertTrue(expectedOutputFile.exists());
    }

    @Before
    public void setUp() {
        command = new CreateJobCommand(testId);
    }

    @After
    public void cleanUp() {
        createdFiles.forEach(f -> f.delete());
        createdDirectories.forEach(d -> d.delete());
    }
}
