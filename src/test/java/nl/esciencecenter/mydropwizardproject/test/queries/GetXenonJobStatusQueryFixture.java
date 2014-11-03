package nl.esciencecenter.mydropwizardproject.test.queries;

import nl.esciencecenter.mydropwizardproject.XenonManager;
import nl.esciencecenter.mydropwizardproject.queries.GetXenonJobStatusQuery;

public class GetXenonJobStatusQueryFixture extends GetXenonJobStatusQuery {
    public void setXenonManager(XenonManager xenonManager) {
        this.xenonManager = xenonManager;
    }
}
