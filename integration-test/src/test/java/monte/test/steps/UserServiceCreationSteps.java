package monte.test.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import monte.test.utils.EntityFactory;

public class UserServiceCreationSteps extends AbstractSteps {

    @Given("Api user from {string} without id")
    public void createApiUserWithoutId(String templateName) throws Exception {
        var apiUser = EntityFactory.createApiUser(toFilePath(templateName));
        testContext().setApiUser(apiUser);
    }

    @When("I send post request")
    public void sendPostRequest() {
        var response = testUserApiClient.postUser(testContext().getApiUser());
        testContext().setResponse(response);
    }

}
