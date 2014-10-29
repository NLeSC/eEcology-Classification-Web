package nl.esciencecenter.mydropwizardproject.queries;

public interface Query<Parameters extends QueryParameters, Result extends QueryResult> {
    public Result run(Parameters parameters);
}
