package nl.esciencecenter.mydropwizardproject.queries;


public class XenonJobStatusQueryParameters implements QueryParameters {

    private String xenonJobId;

    public void setXenonJobId(String xenonJobId) {
        this.xenonJobId = xenonJobId;
    }

    public String getXenonJobId() {
        return xenonJobId;
    }
}
