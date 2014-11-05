package nl.esciencecenter.mydropwizardproject.queries;

public interface Query<Parameters, Result> {
    public Result run(Parameters parameters);
}
