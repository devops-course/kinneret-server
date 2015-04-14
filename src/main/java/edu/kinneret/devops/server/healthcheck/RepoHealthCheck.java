package edu.kinneret.devops.server.healthcheck;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by tsadok on 16/02/2015.
 */
public class RepoHealthCheck extends HealthCheck {
    private final String repositoryBasePath;

    public RepoHealthCheck(String repositoryBasePath) {
        this.repositoryBasePath = repositoryBasePath;
    }

    @Override
    protected Result check() throws Exception {

        //Check access to Repo here
        /*final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }*/
        return Result.healthy();
    }
}