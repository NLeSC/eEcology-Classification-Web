package nl.esciencecenter.mydropwizardproject.test.queries;

import nl.esciencecenter.mydropwizardproject.XenonManager;
import nl.esciencecenter.mydropwizardproject.queries.XenonJobStatusQuery;

public class XenonJobStatusQueryFixture extends XenonJobStatusQuery {
    public void setXenonManager(XenonManager xenonManager) {
        this.xenonManager = xenonManager;
    }
}
