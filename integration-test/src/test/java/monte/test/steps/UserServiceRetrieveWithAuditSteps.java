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

    @Given("DB audit events from {string}")
    public void dbAuditEvents(String templateName) throws Exception {
        var dbUserId = testContext().getDbUser().getId();
        var auditEvents = createAuditList(toFilePath(templateName));
        auditEvents.stream()
                .filter(a -> ID_TO_REPLACE.equals(a.getEntityId()))
                .forEach(t -> t.setEntityId(dbUserId));
        auditEvents.forEach(a -> mongoTemplate.save(a));
        testContext().setAuditEvents(auditEvents.stream()
                .filter(a -> dbUserId.equals(a.getEntityId()))
                .collect(Collectors.toList()));
    }

    @Given("DB audit events from {string} with no records for user")
    public void dbAuditEventsWithNoRecordsForUser(String templateName) throws Exception {
        var auditEvents = createAuditList(toFilePath(templateName));
        auditEvents.stream()
                .filter(a -> ID_TO_REPLACE.equals(a.getEntityId()))
                .forEach(t -> t.setEntityId("NOT_MATCHED_ID"));
        auditEvents.forEach(a -> mongoTemplate.save(a));
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
        var expectedUser = TestApiUserWithAudit.builder()
                .user(testContext().getApiUser())
                .auditEvents(testContext().getAuditEvents())
                .build();
        var responseUser = (TestApiUserWithAudit) testContext().getResponse().getBody();
        assertThat(responseUser).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .isEqualTo(expectedUser);
    }

}
