package edu.kinneret.devops.server.healthcheck;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by tsadok on 16/02/2015.
 * This is only a demo health checker that always returns OK
 */
public class MyHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}