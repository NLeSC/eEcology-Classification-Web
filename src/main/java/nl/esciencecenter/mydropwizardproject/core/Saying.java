package nl.esciencecenter.mydropwizardproject.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Saying {
    private final String id;
    private final boolean isJobReady;

    public Saying(String id, boolean isJobReady) {
        this.id = id;
        this.isJobReady = isJobReady;

    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public boolean isJobReady() {
        return isJobReady;
    }
}