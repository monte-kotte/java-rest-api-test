package monte.test.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import monte.test.model.api.TestApiUser;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceUpdateSteps extends AbstractSteps {

    @When("I send update user request")
    public void sendUpdateUserRequest() {
        var userId = testContext().getDbUser().getId();
        var response = testUserApiClient.updateUser(userId, testContext().getApiUser());
        testContext().setResponse(response);
    }

    @Then("I validate that response user id equals db user id")
    public void validateResponseUserId() {
        var responseUser = (TestApiUser) testContext().getResponse().getBody();
        assertThat(responseUser.getId()).as("Verify id")
                .isEqualTo(testContext().getDbUser().getId());
    }

}
