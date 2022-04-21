package monte.test.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import monte.test.model.api.TestApiUser;
import monte.test.utils.EntityFactory;

import static monte.test.utils.Constants.Files.TEMPLATE_API_USER_1;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceCreationSteps extends AbstractSteps {

    @Given("Api user without id")
    public void createApiUserWithoutId() throws Exception {
        var apiUser = EntityFactory.createApiUser(TEMPLATE_API_USER_1);
        testContext().setApiUser(apiUser);
    }

    @When("I send post request")
    public void sendPostRequest() {
        var response = testUserApiClient.postUser(testContext().getApiUser());
        testContext().setResponse(response);
    }

    @Then("I validate that response body contains api user with generated id")
    public void validateResponseBody() {
        var responseUser = (TestApiUser) testContext().getResponse().getBody();
        assertThat(responseUser).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields("id")
                .isEqualTo(testContext().getApiUser());
        assertThat(responseUser.getId()).isNotNull();
    }

}
