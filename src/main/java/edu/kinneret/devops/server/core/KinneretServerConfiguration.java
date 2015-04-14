package edu.kinneret.devops.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by tsadok on 16/02/2015.
 */
public class KinneretServerConfiguration extends Configuration {
    @NotEmpty
    private String repositoryBasePath;

    @JsonProperty
    public String getRepositoryBasePath() {
        return repositoryBasePath;
    }

    @JsonProperty
    public void setRepositoryBasePath(String repositoryBasePath) {
        this.repositoryBasePath = repositoryBasePath;
    }
}
