package nl.esciencecenter.mydropwizardproject.test.commands;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import nl.esciencecenter.mydropwizardproject.PathManagerFixture;
import nl.esciencecenter.mydropwizardproject.XenonManagerFixture;
import nl.esciencecenter.mydropwizardproject.domain.JobStatus;
import nl.esciencecenter.mydropwizardproject.test.queries.JobStatusQueryFixture;
import nl.esciencecenter.xenon.jobs.Job;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateJobStatusCommandTest {
    private final UUID testId = UUID.fromString("13773550-6423-11e4-9803-0800200c9a66");
    private UpdateJobStatusCommandFixture command;
    private PathManagerFixture pathManager;
    private final String unfinishedStatusFileName = "unfinishedstatus.json";
    private final String finishedStatusFileName = "finishedstatus.json";
    private XenonJobStatusQueryFixture xenonJobStatusQuery;

    @Test
    public void oldDoneIsFalseNewIsTrue_updatedFileIsTrue() throws IOException {
        // Arrange
        File unfinishedStatusFile = new File(pathManager.getJobPath(testId).resolve(unfinishedStatusFileName).toString());
        File currentFile = new File(pathManager.getJobStatusFilePath(testId).toString());
        FileUtils.copyFile(unfinishedStatusFile, currentFile);
        xenonJobStatusQuery.setXenonManager(getXenonManagerThatReturnsStatusDone());

        // Act
        command.execute(testId);

        // Assert
        JobStatus jobStatus = new ObjectMapper().readValue(currentFile, JobStatus.class);
        assertTrue(jobStatus.isDone());
    }

    @Test
    public void oldDoneIsTrueNewIsFalse_updatedFileIsTrue() throws IOException {
        // Arrange
        File finishedStatusFile = new File(pathManager.getJobPath(testId).resolve(finishedStatusFileName).toString());
        File currentFile = new File(pathManager.getJobStatusFilePath(testId).toString());
        FileUtils.copyFile(finishedStatusFile, currentFile);
        xenonJobStatusQuery.setXenonManager(getXenonManagerThatReturnsStatusNotDone());

        // Act
        command.execute(testId);

        // Assert
        JobStatus jobStatus = new ObjectMapper().readValue(currentFile, JobStatus.class);
        assertTrue(jobStatus.isDone());
    }

    @Before
    public void setUp() {
        pathManager = new PathManagerFixture();
        ObjectMapper objectMapper = new ObjectMapper();
        xenonJobStatusQuery = new XenonJobStatusQueryFixture();

        command = new UpdateJobStatusCommandFixture();
        command.setPathManager(pathManager);
        command.setObjectMapper(objectMapper);
        command.setXenonJobStatusQuery(xenonJobStatusQuery);
        command.setJobStatusQuery(getJobStatusQuery(objectMapper, pathManager));
    }

    @After
    public void cleanUp() {
        FileUtils.deleteQuietly(new File(pathManager.getJobStatusFilePath(testId).toString()));
    }

    private JobStatusQueryFixture getJobStatusQuery(ObjectMapper objectMapper, PathManagerFixture pathManager) {
        JobStatusQueryFixture jobStatusQuery = new JobStatusQueryFixture();
        jobStatusQuery.setObjectMapper(objectMapper);
        jobStatusQuery.setPathManager(pathManager);
        return jobStatusQuery;
    }

    private XenonManagerFixture getXenonManagerThatReturnsStatusDone() {
        return new XenonManagerFixture() {
            @Override
            public nl.esciencecenter.xenon.jobs.JobStatus getXenonJobStatus(String xenonJobId) {
                return getDoneXenonJobStatus();
            }

            private nl.esciencecenter.xenon.jobs.JobStatus getDoneXenonJobStatus() {
                return new nl.esciencecenter.xenon.jobs.JobStatus() {

                    @Override
                    public boolean isRunning() {
                        return false;
                    }

                    @Override
                    public boolean isDone() {
                        return true;
                    }

                    @Override
                    public boolean hasException() {
                        return false;
                    }

                    @Override
                    public String getState() {
                        return null;
                    }

                    @Override
                    public Map<String, String> getSchedulerSpecficInformation() {
                        return null;
                    }

                    @Override
                    public Job getJob() {
                        return null;
                    }

                    @Override
                    public Integer getExitCode() {
                        return null;
                    }

                    @Override
                    public Exception getException() {
                        return null;
                    }
                };
            }
        };
    }

    private XenonManagerFixture getXenonManagerThatReturnsStatusNotDone() {
        return new XenonManagerFixture() {
            @Override
            public nl.esciencecenter.xenon.jobs.JobStatus getXenonJobStatus(String xenonJobId) {
                return getDoneXenonJobStatus();
            }

            private nl.esciencecenter.xenon.jobs.JobStatus getDoneXenonJobStatus() {
                return new nl.esciencecenter.xenon.jobs.JobStatus() {

                    @Override
                    public boolean isRunning() {
                        return false;
                    }

                    @Override
                    public boolean isDone() {
                        return false;
                    }

                    @Override
                    public boolean hasException() {
                        return false;
                    }

                    @Override
                    public String getState() {
                        return null;
                    }

                    @Override
                    public Map<String, String> getSchedulerSpecficInformation() {
                        return null;
                    }

                    @Override
                    public Job getJob() {
                        return null;
                    }

                    @Override
                    public Integer getExitCode() {
                        return null;
                    }

                    @Override
                    public Exception getException() {
                        return null;
                    }
                };
            }
        };
    }
}
