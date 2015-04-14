package edu.kinneret.devops.server.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tsadok on 14/04/2015.
 */
public class Task {

    private long id;
    private String description;

    public Task() {
        // Jackson deserialization
    }

    public Task(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }
}