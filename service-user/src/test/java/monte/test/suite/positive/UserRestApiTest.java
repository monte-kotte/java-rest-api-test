package monte.test.suite.positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import monte.test.model.api.audit.TestAudit;
import monte.test.model.db.TestDbUser;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static monte.test.model.api.audit.TestAudit.TestAction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UserRestApiTest extends AbstractTest {

    @Autowired
    MongoTemplate mongoTemplate;

    static Stream<Arguments> testDataProvider() {
        return Stream.of(
                arguments("driverLicense", null),
                arguments("addresses[0].zip", "12345-6789"),
                arguments("addresses[0].apartmentNumber", null),
                arguments("addresses[0].apartmentNumber", ""),
                arguments("addresses[0].apartmentNumber", "   ")
        );
    }

    @Test
    void createUserTest() throws IOException {
        var user = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        stubFor(post(urlEqualTo("/audit-events"))
                // .withRequestBody() - ignore body allow any
                .willReturn(aResponse().withStatus(200)));

        var response = restTemplate.postForEntity("/users", user, TestApiUser.class);
        var responseUserId = response.getBody().getId();

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields("id")
                .isEqualTo(user);
        assertThat(responseUserId).isNotNull();
        verify(postRequestedFor(urlEqualTo("/audit-events")).
                withRequestBody(equalToJson(
                        userAuditJson(responseUserId, TestAction.CREATE, "create new user")
                )));
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void createUserTest(String fieldName, Object fieldValue) throws Exception {
        var user = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        PropertyUtils.setNestedProperty(user, fieldName, fieldValue);
        stubFor(post(urlEqualTo("/audit-events"))
                .willReturn(aResponse().withStatus(200)));

        var response = restTemplate.postForEntity("/users", user, TestApiUser.class);
        var responseUserId = response.getBody().getId();

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields("id")
                .isEqualTo(user);
        assertThat(responseUserId).isNotNull();
        verify(postRequestedFor(urlEqualTo("/audit-events")).
                withRequestBody(equalToJson(
                        userAuditJson(responseUserId, TestAction.CREATE, "create new user")
                )));
    }

    @Test
    void getAllUsersTest() throws IOException {
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

    @Test
    void updateUserTest() throws IOException {
        var dbUser = mongoTemplate.save(fromFile(TEMPLATE_DB_USER_1, TestDbUser.class));
        var dbUserId = dbUser.getId();
        var apiUser = fromFile(TEMPLATE_API_USER_2, TestApiUser.class);
        stubFor(post(urlEqualTo("/audit-events"))
                .willReturn(aResponse().withStatus(200)));

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
        verify(postRequestedFor(urlEqualTo("/audit-events")).
                withRequestBody(equalToJson(
                        userAuditJson(dbUserId, TestAction.UPDATE, "update user")
                )));
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void updateUserTest(String fieldName, Object fieldValue) throws Exception {
        var dbUser = mongoTemplate.save(fromFile(TEMPLATE_DB_USER_1, TestDbUser.class));
        var dbUserId = dbUser.getId();
        var apiUser = fromFile(TEMPLATE_API_USER_2, TestApiUser.class);
        PropertyUtils.setNestedProperty(apiUser, fieldName, fieldValue);
        stubFor(post(urlEqualTo("/audit-events"))
                .willReturn(aResponse().withStatus(200)));

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
        verify(postRequestedFor(urlEqualTo("/audit-events")).
                withRequestBody(equalToJson(
                        userAuditJson(dbUserId, TestAction.UPDATE, "update user")
                )));
    }

    private String userAuditJson(String id, TestAudit.TestAction testAction, String comment) throws JsonProcessingException {
        var auditRecord = TestAudit.builder()
                .entityName("User")
                .entityId(id)
                .action(testAction)
                .comment(comment)
                .build();
        return objectMapper.writeValueAsString(auditRecord);
    }

}
