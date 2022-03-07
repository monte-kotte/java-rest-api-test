package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import monte.test.model.api.audit.TestAudit;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateTest extends AbstractTest {

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
                        userAuditJson(responseUserId, TestAudit.TestAction.CREATE, "create new user")
                )));
    }

    @ParameterizedTest
    @MethodSource("monte.test.data.UserDataProvider#testData")
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
                        userAuditJson(responseUserId, TestAudit.TestAction.CREATE, "create new user")
                )));
    }

}
