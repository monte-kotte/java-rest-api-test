package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.api.TestApiUserWithAudit;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static monte.test.utils.Constants.Files.*;
import static monte.test.utils.Constants.TestData.ID_TO_REPLACE;
import static monte.test.utils.EntityFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserWithAuditRetrieveTest extends AbstractTest {

    @Test
    void getUserWithAuditByIdTest() throws IOException {
        var dbUser = mongoTemplate.save(createDbUser(TEMPLATE_DB_USER_1));
        var dbUserId = dbUser.getId();
        var expectedApiUser = createApiUser(TEMPLATE_API_USER_1, dbUser.getId());

        var auditEvents = createAuditList(TEMPLATE_API_AUDIT_1);
        auditEvents.stream()
                .filter(a -> ID_TO_REPLACE.equals(a.getEntityId()))
                .forEach(t -> t.setEntityId(dbUserId));
        stubFor(get(urlEqualTo("/audit-events"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-type", "application/json")
                        .withBody(toJson(auditEvents))
                ));

        var expectedUserWithAudit = TestApiUserWithAudit.builder()
                .user(expectedApiUser)
                .auditEvents(auditEvents)
                .build();

        var response = restTemplate.getForEntity("/users-with-audit/" + dbUserId, TestApiUserWithAudit.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .isEqualTo(expectedUserWithAudit);
    }

    @Test
    void getUserWithAuditById_ManyRecordsTest() throws IOException {
        var dbUser = mongoTemplate.save(createDbUser(TEMPLATE_DB_USER_1));
        var dbUserId = dbUser.getId();
        var expectedApiUser = createApiUser(TEMPLATE_API_USER_1, dbUser.getId());

        var auditEvents = createAuditList(TEMPLATE_API_AUDIT_2);
        auditEvents.stream()
                .filter(a -> ID_TO_REPLACE.equals(a.getEntityId()))
                .forEach(t -> t.setEntityId(dbUserId));
        stubFor(get(urlEqualTo("/audit-events"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-type", "application/json")
                        .withBody(toJson(auditEvents))
                ));

        var expectedUserWithAudit = TestApiUserWithAudit.builder()
                .user(expectedApiUser)
                .auditEvents(auditEvents
                        .stream()
                        .filter(a -> Objects.equals(dbUserId, a.getEntityId()))
                        .collect(Collectors.toList()))
                .build();

        var response = restTemplate.getForEntity("/users-with-audit/" + dbUserId, TestApiUserWithAudit.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .isEqualTo(expectedUserWithAudit);
    }

}
