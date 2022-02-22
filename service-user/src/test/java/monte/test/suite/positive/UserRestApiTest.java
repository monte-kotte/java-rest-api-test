package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import monte.test.model.db.TestDbUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRestApiTest extends AbstractTest {

    @Autowired
    MongoTemplate mongoTemplate;

    public static final String CASE1_API_USER = "src/test/resources/json/case1-api-user.json";
    public static final String CASE2_DB_USER = "src/test/resources/json/case2-db-user.json";
    public static final String CASE2_API_USER = "src/test/resources/json/case2-api-user.json";

    @Test
    @DisplayName("CASE1 - create new user")
    void createUserTest() throws IOException {
        var user = fromFile(CASE1_API_USER, TestApiUser.class);
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
    @DisplayName("CASE2 - get all users")
    void getAllUsers() throws IOException {
        // prep
        var dbUser = mongoTemplate.save(fromFile(CASE2_DB_USER, TestDbUser.class));
        var expectedApiUser = fromFile(CASE2_API_USER, TestApiUser.class);
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

    private <T> T fromFile(String fileName, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File(fileName), clazz);
    }

}
