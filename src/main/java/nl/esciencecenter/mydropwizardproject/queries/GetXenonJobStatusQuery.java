package nl.esciencecenter.mydropwizardproject.queries;

import nl.esciencecenter.mydropwizardproject.XenonManager;
import nl.esciencecenter.xenon.jobs.JobStatus;

import com.google.inject.Inject;

public class GetXenonJobStatusQuery implements Query<GetXenonJobStatusQueryParameters, GetXenonJobStatusQueryResult> {
    @Inject
    protected XenonManager xenonManager;

    @Override
    public GetXenonJobStatusQueryResult run(GetXenonJobStatusQueryParameters parameters) {
        GetXenonJobStatusQueryResult result = new GetXenonJobStatusQueryResult();
        JobStatus xenonJobStatus = xenonManager.getXenonJobStatus(parameters.getXenonJobId());
        result.setDone(xenonJobStatus == null ? false : xenonJobStatus.isDone());
        return result;
    }

}
