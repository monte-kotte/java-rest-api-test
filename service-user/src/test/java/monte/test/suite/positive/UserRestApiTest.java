package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import monte.test.model.db.TestDbUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRestApiTest extends AbstractTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void createUserTest() throws IOException {
        var user = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        var response = restTemplate.postForEntity("/users", user, TestApiUser.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields("id")
                .isEqualTo(user);
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void getAllUsers() throws IOException {
        // prep
        var dbUser = mongoTemplate.save(fromFile(TEMPLATE_DB_USER_1, TestDbUser.class));
        var expectedApiUser = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        expectedApiUser.setId(dbUser.getId());
        var expectedDbSize = mongoTemplate.getCollection("user").countDocuments();

        // execute
        var response = restTemplate.exchange("/users", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TestApiUser>>() {
                });

        // assert
        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify user")
                .contains(expectedApiUser);
        assertThat(response.getBody().size()).as("Verify db size")
                .isEqualTo(expectedDbSize);
    }

    @Test
    void getUserById() throws IOException {
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

    @Test
    void updateUser() throws IOException {
        var dbUser = mongoTemplate.save(fromFile(TEMPLATE_DB_USER_1, TestDbUser.class));
        var dbUserId = dbUser.getId();
        var apiUser = fromFile(TEMPLATE_API_USER_2, TestApiUser.class);

        var userEntity = new HttpEntity<>(apiUser);
        var response = restTemplate.exchange("/users/" + dbUserId,
                HttpMethod.PUT, userEntity, TestApiUser.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields("id")
                .isEqualTo(apiUser);
        assertThat(response.getBody().getId()).as("Verify id")
                .isEqualTo(dbUserId);
    }

}
