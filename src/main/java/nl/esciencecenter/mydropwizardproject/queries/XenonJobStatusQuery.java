package nl.esciencecenter.mydropwizardproject.queries;

import nl.esciencecenter.mydropwizardproject.XenonManager;
import nl.esciencecenter.xenon.jobs.JobStatus;

import com.google.inject.Inject;

public class XenonJobStatusQuery implements Query<XenonJobStatusQueryParameters, XenonJobStatusQueryResult> {
    @Inject
    protected XenonManager xenonManager;

    @Override
    public XenonJobStatusQueryResult run(XenonJobStatusQueryParameters parameters) {
        XenonJobStatusQueryResult result = new XenonJobStatusQueryResult();
        JobStatus xenonJobStatus = xenonManager.getXenonJobStatus(parameters.getXenonJobId());
        result.setDone(xenonJobStatus == null ? false : xenonJobStatus.isDone());
        return result;
    }

}
