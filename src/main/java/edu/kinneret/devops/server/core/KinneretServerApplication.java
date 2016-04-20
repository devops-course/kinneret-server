package edu.kinneret.devops.server.core;

import edu.kinneret.devops.server.dao.TaskDao;
import edu.kinneret.devops.server.healthcheck.RepoHealthCheck;
import edu.kinneret.devops.server.rest.TasksResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;

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

        initRepo(configuration.getRepositoryBasePath());

        final TaskDao taskDao = new TaskDao(configuration.getRepositoryBasePath());
        final TasksResource resource = new TasksResource(taskDao);

        environment.jersey().register(resource);

        final RepoHealthCheck healthCheck =
                new RepoHealthCheck(configuration.getRepositoryBasePath());

        environment.healthChecks().register("repositoryAccess", healthCheck);

    }

    private void initRepo(final String repoBasePath)
    {
        File repo = new File(repoBasePath);

        // if the directory does not exist, create it
        if (!repo.exists()) {
            System.out.println("creating repo directory: " + repoBasePath);
            boolean result;
            try{
                result = repo.mkdirs();
            }
            catch(SecurityException ex){
                throw new RuntimeException("Failed to create repo directory!", ex);
            }

            if (result)
            {
                System.out.println("repo directory: " + repoBasePath + ", was successfully created.");
            }
            else {
                throw new RuntimeException("Failed to create repo directory! function returned false");
            }
        }
    }
}