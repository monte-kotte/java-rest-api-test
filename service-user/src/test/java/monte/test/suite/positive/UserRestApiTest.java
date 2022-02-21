package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRestApiTest extends AbstractTest {

    public static final String REQUEST_USER_FILE_NAME = "src/test/resources/json/request-user.json";

    @Test
    void createUserTest() throws IOException {
        var user = getUserFromFile(REQUEST_USER_FILE_NAME);
        var response = restTemplate.postForEntity("/users", user, TestUser.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields("id")
                .isEqualTo(user);
        assertThat(response.getBody().getId()).isNotNull();
    }

    private TestUser getUserFromFile(String fileName) throws IOException {
        return objectMapper.readValue(new File(fileName), TestUser.class);
    }

}
