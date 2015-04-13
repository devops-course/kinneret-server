package edu.kinneret.devops.server.core;

import edu.kinneret.devops.server.rest.TasksResource;
import edu.kinneret.devops.server.healthcheck.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by tsadok on 16/02/2015.
 */
public class KinneretServerApplication extends Application<KinneretServerConfiguration> {
    public static void main(String[] args) throws Exception {
        new KinneretServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "kinneret-server";
    }

    @Override
    public void initialize(Bootstrap<KinneretServerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(KinneretServerConfiguration configuration,
                    Environment environment) {
        final TasksResource resource = new TasksResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}