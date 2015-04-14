package rest;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import edu.kinneret.devops.server.rest.Task;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created by tsadok on 14/04/2015.
 */
public class TestTask {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Task task = new Task(1, "description to test");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/task.json"), Task.class));

        assertThat(MAPPER.writeValueAsString(task)).isEqualTo(expected);
    }
}
