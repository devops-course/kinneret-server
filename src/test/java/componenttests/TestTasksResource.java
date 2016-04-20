package componenttests;

import edu.kinneret.devops.server.dao.TaskDao;
import edu.kinneret.devops.server.rest.Task;
import edu.kinneret.devops.server.rest.TasksResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by tsadok on 05/05/2015.
 */
public class TestTasksResource {

        private static final TaskDao dao = mock(TaskDao.class);

        @ClassRule
        public static final ResourceTestRule resources = ResourceTestRule.builder()
                .addResource(new TasksResource(dao))
                .build();

        private final Task task = new Task(123, "desc123");


        @Test
        public void testGetSingleTask() {
            when(dao.getSingleTask(eq("123"))).thenReturn(task);

            assertThat(resources.client().target("/tasks/123").request().get(Task.class))
                    .isEqualTo(task);

            verify(dao).getSingleTask("123");
        }
}
