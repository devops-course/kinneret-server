package edu.kinneret.devops.server.rest;

import com.codahale.metrics.annotation.Timed;
import edu.kinneret.devops.server.dao.TaskDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

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
    public Response createTask(Task taskToCreate)
    {
        return Response.status(Response.Status.CREATED).entity(dao.createTask(taskToCreate)).build();
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
        Task t = dao.getSingleTask(id);
        return t;
    }

    @DELETE @Path("/{id}")
    @Timed
    public Task deleteTask(@PathParam("id") String id)
    {
        return dao.deleteTask(id);
    }
}