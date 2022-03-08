package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static monte.test.model.api.audit.TestAudit.TestAction;
import static monte.test.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserUpdateTest extends AbstractTest {

    @Test
    void updateUserTest() throws IOException {
        var dbUser = mongoTemplate.save(createDbUser(TEMPLATE_DB_USER_1));
        var dbUserId = dbUser.getId();
        var apiUser = createApiUser(TEMPLATE_API_USER_2, dbUser.getId());;
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
    @MethodSource("monte.test.utils.TestDataFactory#testData")
    void updateUserTest(String fieldName, Object fieldValue) throws Exception {
        var dbUser = mongoTemplate.save(createDbUser(TEMPLATE_DB_USER_1));
        var dbUserId = dbUser.getId();
        var apiUser = createApiUser(TEMPLATE_API_USER_2, dbUser.getId());;
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

}
