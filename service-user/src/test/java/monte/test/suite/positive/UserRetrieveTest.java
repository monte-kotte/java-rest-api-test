package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import monte.test.model.db.TestDbUser;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRetrieveTest extends AbstractTest {

    @Test
    void getAllUsersTest() throws IOException {
        var dbUser = mongoTemplate.save(fromFile(TEMPLATE_DB_USER_1, TestDbUser.class));
        var expectedApiUser = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        expectedApiUser.setId(dbUser.getId());
        var expectedDbSize = mongoTemplate.getCollection("user").countDocuments();

        var response = restTemplate.exchange("/users", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TestApiUser>>() {
                });

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify user")
                .contains(expectedApiUser);
        assertThat(response.getBody().size()).as("Verify db size")
                .isEqualTo(expectedDbSize);
    }

    @Test
    void getUserByIdTest() throws IOException {
        var dbUser = mongoTemplate.save(fromFile(TEMPLATE_DB_USER_1, TestDbUser.class));
        var dbUserId = dbUser.getId();
        var expectedApiUser = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        expectedApiUser.setId(dbUserId);

        var response = restTemplate.getForEntity("/users/" + dbUserId, TestApiUser.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .isEqualTo(expectedApiUser);
    }

}
