package monte.test.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import monte.test.model.api.TestApiUserWithAudit;

import java.util.List;
import java.util.stream.Collectors;

import static monte.test.utils.Constants.TestData.ID_TO_REPLACE;
import static monte.test.utils.EntityFactory.createAuditList;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceRetrieveWithAuditSteps extends AbstractSteps {

    @Given("Audit events from {string}")
    public void auditEvents(String templateName) throws Exception {
        var dbUserId = testContext().getDbUser().getId();
        var auditEvents = createAuditList(toFilePath(templateName));
        auditEvents.stream()
                .filter(a -> ID_TO_REPLACE.equals(a.getEntityId()))
                .forEach(t -> t.setEntityId(dbUserId));
        auditEvents.forEach(a -> mongoTemplate.save(a, "audit"));
        testContext().setAuditEvents(auditEvents.stream()
                .filter(a -> dbUserId.equals(a.getEntityId()))
                .collect(Collectors.toList()));
    }

    @Given("Audit events from {string} with no records for user")
    public void auditEventsWithNoRecordsForUser(String templateName) throws Exception {
        var auditEvents = createAuditList(toFilePath(templateName));
        auditEvents.stream()
                .filter(a -> ID_TO_REPLACE.equals(a.getEntityId()))
                .forEach(t -> t.setEntityId("NOT_MATCHED_ID"));
        auditEvents.forEach(a -> mongoTemplate.save(a, "audit"));
        testContext().setAuditEvents(List.of());
    }

    @When("I send get user with audit request")
    public void sendGetUserWithAuditRequest() {
        var userId = testContext().getDbUser().getId();
        var response = testUserApiClient.getUserWithAuditById(userId);
        testContext().setResponse(response);
    }

    @Then("I validate that response body equals user with audit")
    public void validateResponseBody() {
        var responseUser = (TestApiUserWithAudit) testContext().getResponse().getBody();
        assertThat(responseUser).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .isEqualTo(TestApiUserWithAudit.builder()
                        .user(testContext().getApiUser())
                        .auditEvents(testContext().getAuditEvents())
                        .build());
    }

}
