package edu.kinneret.devops.server.dao;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kinneret.devops.server.rest.Task;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tsadok on 05/05/2015.
 */
public class TaskDao {

    private final File tasksRepo;
    private static AtomicInteger counter = new AtomicInteger(1);

    public TaskDao(String repositoryBasePath)
    {
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

    public Task getSingleTask(String id)
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

    public Task deleteTask(String id)
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
