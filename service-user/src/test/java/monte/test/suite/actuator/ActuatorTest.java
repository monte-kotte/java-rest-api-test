package monte.test.suite.actuator;

import monte.test.AbstractTest;
import monte.test.model.actuator.Health;
import monte.test.model.actuator.Info;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ActuatorTest extends AbstractTest {

    @Test
    void appHealthTest() {
        var response = restTemplate.getForEntity("/actuator/health", Health.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).as("Verify application status")
                .isEqualTo(Status.UP);
    }

    @Test
    void appInfoTest() {
        var expectedAppInfo = Info.Application.builder()
                .version("1.0-SNAPSHOT")
                .build();

        var response = restTemplate.getForEntity("/actuator/info", Info.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getApp()).as("Verify application info")
                .isEqualTo(expectedAppInfo);
    }

}
