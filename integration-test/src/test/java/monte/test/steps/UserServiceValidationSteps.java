package monte.test.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import monte.test.model.api.error.TestValidationErrorResponse;
import monte.test.model.api.error.TestViolation;
import monte.test.utils.ConfigFileReader;
import org.springframework.http.HttpEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceValidationSteps extends AbstractSteps {

    @When("I send post request with incorrect user entity")
    public void sendPostRequestWithIncorrectUser() {
        var url = ConfigFileReader.getUserServiceUrl() + "/users";
        var userEntity = new HttpEntity<>(testContext().getApiUser());
        var errorResponse = restTemplate.postForEntity(url, userEntity, TestValidationErrorResponse.class);
        testContext().setResponse(errorResponse);
    }

    @Then("I validate that response contains violation ([\\s\\S]+) with ([\\s\\S]+)$")
    public void validateThatResponseContainsViolation(String fieldName, String violationMessage) {
        var errorResponse = (TestValidationErrorResponse) testContext().getResponse().getBody();
        assertThat(errorResponse.getViolations()).as("Verify response")
                .containsExactlyInAnyOrder(TestViolation.builder()
                        .fieldName(fieldName)
                        .message(violationMessage)
                        .build());
    }

}
