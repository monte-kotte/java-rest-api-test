package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import monte.test.model.api.audit.TestAudit;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static monte.test.utils.Constants.Files.TEMPLATE_API_USER_1;
import static monte.test.utils.Constants.Files.TEMPLATE_DB_USER_1;
import static monte.test.utils.Constants.TestData.AUDIT_READ_COMMENT;
import static monte.test.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRetrieveTest extends AbstractTest {

    @Test
    void getAllUsersTest() throws IOException {
        var dbUser = mongoTemplate.save(createDbUser(TEMPLATE_DB_USER_1));
        var expectedApiUser = createApiUser(TEMPLATE_API_USER_1, dbUser.getId());
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
        var dbUser = mongoTemplate.save(createDbUser(TEMPLATE_DB_USER_1));
        var dbUserId = dbUser.getId();
        var expectedApiUser = createApiUser(TEMPLATE_API_USER_1, dbUser.getId());
        stubFor(post(urlEqualTo("/audit-events"))
                .willReturn(aResponse().withStatus(200)));

        var response = restTemplate.getForEntity("/users/" + dbUserId, TestApiUser.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .isEqualTo(expectedApiUser);
        verify(postRequestedFor(urlEqualTo("/audit-events")).
                withRequestBody(equalToJson(
                        userAuditJson(dbUserId, TestAudit.TestAction.READ, AUDIT_READ_COMMENT)
                )));
    }

}
