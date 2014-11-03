package nl.esciencecenter.mydropwizardproject;

import io.dropwizard.lifecycle.Managed;

import java.util.Arrays;
import java.util.UUID;

import nl.esciencecenter.xenon.Xenon;
import nl.esciencecenter.xenon.XenonException;
import nl.esciencecenter.xenon.XenonFactory;
import nl.esciencecenter.xenon.jobs.Job;
import nl.esciencecenter.xenon.jobs.JobDescription;
import nl.esciencecenter.xenon.jobs.JobStatus;
import nl.esciencecenter.xenon.jobs.Scheduler;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XenonManager implements Managed {
    @Inject
    PathManager pathManager;

    private Xenon xenon;
    private Scheduler scheduler;

    public Xenon getXenon() {
        if (xenon == null) {
            startNewXenon();
        }
        return xenon;
    }

    @Override
    public void start() {
        startNewXenon();
    }

    @Override
    public void stop() {
        try {
            xenon.jobs().close(scheduler);
        } catch (XenonException e) {
            throw getGenericXenonException(e);
        }
    }

    public String createXenonJobAndReturnXenonJobId(UUID jobId) {
        JobDescription jobDescription = createJobDescription(jobId);
        try {
            Job job = xenon.jobs().submitJob(scheduler, jobDescription);
            return job.getIdentifier();
        } catch (XenonException e) {
            throw getGenericXenonException(e);
        }
    }

    public JobStatus getXenonJobStatus(String xenonJobId) {

        try {
            String[] queueNames = scheduler.getQueueNames();
            Job[] jobs = xenon.jobs().getJobs(scheduler, queueNames);
            Job targetJob = Arrays.stream(jobs).filter(job -> job.getIdentifier() == xenonJobId).findFirst().orElse(null);
            if (targetJob == null) {
                return null;
            }
            return xenon.jobs().getJobStatus(targetJob);

        } catch (XenonException e) {
            throw getGenericXenonException(e);
        }

    }

    private RuntimeException getGenericXenonException(XenonException e) {
        return new RuntimeException("Xenon threw an exception.", e);
    }

    private JobDescription createJobDescription(UUID jobId) {
        String jobDirectory = pathManager.getJobPath(jobId).toString();
        JobDescription jobDescription = new JobDescription();
        jobDescription.setExecutable("java");
        jobDescription.setArguments("-jar", "classificationtool.jar", jobDirectory);
        jobDescription.setWorkingDirectory("classificationtool");
        jobDescription.setStdout(jobDirectory + "/stdout.txt");
        jobDescription.setStderr(jobDirectory + "/stderr.txt");
        return jobDescription;
    }

    private void startNewXenon() {
        try {
            xenon = XenonFactory.newXenon(null);
            scheduler = xenon.jobs().newScheduler("local", null, null, null);
        } catch (XenonException e) {
            throw getGenericXenonException(e);
        }
    }

}
