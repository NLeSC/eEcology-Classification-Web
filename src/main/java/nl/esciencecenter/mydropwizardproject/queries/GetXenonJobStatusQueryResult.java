package nl.esciencecenter.mydropwizardproject.queries;

public class GetXenonJobStatusQueryResult implements QueryResult {

    private boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
}
