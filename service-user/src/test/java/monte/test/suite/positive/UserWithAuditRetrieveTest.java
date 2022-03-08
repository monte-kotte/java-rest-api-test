package monte.test.suite.positive;

import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import monte.test.model.api.TestApiUserWithAudit;
import monte.test.model.api.audit.TestAudit;
import monte.test.model.db.TestDbUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserWithAuditRetrieveTest extends AbstractTest {

    public static final String ID_TO_REPLACE = "REPLACE_ME_WITH_REAL_ID";

    @Test
    void getUserWithAuditByIdTest() throws IOException {
        var dbUser = mongoTemplate.save(fromFile(TEMPLATE_DB_USER_1, TestDbUser.class));
        var dbUserId = dbUser.getId();
        var expectedApiUser = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        expectedApiUser.setId(dbUserId);

        var auditEvents = Arrays.asList(fromFile(TEMPLATE_API_AUDIT_1, TestAudit[].class));
        auditEvents.stream()
                .filter(a -> ID_TO_REPLACE.equals(a.getEntityId()))
                .forEach(t -> t.setEntityId(dbUserId));
        stubFor(get(urlEqualTo("/audit-events"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-type", "application/json")
                        .withBody(objectMapper.writeValueAsString(auditEvents))
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
        var dbUser = mongoTemplate.save(fromFile(TEMPLATE_DB_USER_1, TestDbUser.class));
        var dbUserId = dbUser.getId();
        var expectedApiUser = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        expectedApiUser.setId(dbUserId);

        var auditEvents = Arrays.asList(fromFile(TEMPLATE_API_AUDIT_2, TestAudit[].class));
        auditEvents.stream()
                .filter(a -> ID_TO_REPLACE.equals(a.getEntityId()))
                .forEach(t -> t.setEntityId(dbUserId));
        stubFor(get(urlEqualTo("/audit-events"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-type", "application/json")
                        .withBody(objectMapper.writeValueAsString(auditEvents))
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
