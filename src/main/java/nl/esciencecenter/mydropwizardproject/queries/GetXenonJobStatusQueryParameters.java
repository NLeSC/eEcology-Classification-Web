package nl.esciencecenter.mydropwizardproject.queries;


public class GetXenonJobStatusQueryParameters implements QueryParameters {

    private String xenonJobId;

    public void setXenonJobId(String xenonJobId) {
        this.xenonJobId = xenonJobId;
    }

    public String getXenonJobId() {
        return xenonJobId;
    }
}
