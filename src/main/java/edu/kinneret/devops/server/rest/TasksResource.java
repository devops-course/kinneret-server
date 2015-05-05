package edu.kinneret.devops.server.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import edu.kinneret.devops.server.dao.TaskDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by tsadok on 16/02/2015.
 */
@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TasksResource {

    private TaskDao dao;

    public TasksResource(TaskDao dao) {
        this.dao = dao;
    }

    @POST
    @Timed
    public Task createTask(Task taskToCreate)
    {
        return dao.createTask(taskToCreate);
    }

    @GET
    @Timed
    public Collection<Task> getTasks() {
        return dao.getTasks();
    }

    @GET @Path("/{id}")
    @Timed
    public Task getSingleTask(@PathParam("id") String id)
    {
        return dao.getSingleTask(id);
    }

    @DELETE @Path("/{id}")
    @Timed
    public Task deleteTask(@PathParam("id") String id)
    {
        return dao.deleteTask(id);
    }
}