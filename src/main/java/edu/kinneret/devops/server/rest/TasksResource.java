package edu.kinneret.devops.server.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

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
    private final File tasksRepo;
    private static AtomicInteger counter = new AtomicInteger(1);

    public TasksResource(String repositoryBasePath) {

        tasksRepo = new File(repositoryBasePath, "tasks");
        if (!tasksRepo.exists())
        {
            try
            {
                tasksRepo.mkdir();
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        else
        {
            File[] tasks = tasksRepo.listFiles();
            for(int i = 0; i < tasks.length; i++)
            {
                File task = tasks[i];
                Integer taskId = Integer.parseInt(task.getName().split("\\.")[0]);
                if (counter.get() < taskId+1)
                    counter.set(taskId+1);
            }
        }

        System.out.println("tasks counter is going to start from: " + counter.get());
    }

    @POST
    @Timed
    public Task createTask(Task taskToCreate)
    {
        int id = counter.getAndIncrement();
        taskToCreate.setId(id);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(tasksRepo, id + ".json"), taskToCreate);
            Task taskFromDisk = mapper.readValue(new File(tasksRepo, id + ".json"), Task.class);
            return taskFromDisk;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @GET
    @Timed
    public Collection<Task> getTasks() {
        try {
            ArrayList<Task> allTasks = new ArrayList<Task>();
            ObjectMapper mapper = new ObjectMapper();
            File[] tasks = tasksRepo.listFiles();

            for (int i = 0; i < tasks.length; i++) {
                Task taskFromDisk = mapper.readValue(tasks[i], Task.class);
                allTasks.add(taskFromDisk);
            }

            return allTasks;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @GET @Path("/{id}")
    @Timed
    public Task getSingleTask(@PathParam("id") String id)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Task taskFromDisk = mapper.readValue(new File(tasksRepo, id + ".json"), Task.class);
            return taskFromDisk;
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @DELETE @Path("/{id}")
    @Timed
    public Task deleteTask(@PathParam("id") String id)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            File taskToDelete = new File(tasksRepo, id + ".json");
            Task taskFromDisk = mapper.readValue(taskToDelete, Task.class);

            taskToDelete.delete();

            return taskFromDisk;

        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}