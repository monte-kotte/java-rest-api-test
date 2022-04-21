package monte.test.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import monte.test.model.api.TestApiUser;

import java.io.IOException;
import java.util.List;

import static monte.test.utils.Constants.Files.TEMPLATE_API_USER_1;
import static monte.test.utils.Constants.Files.TEMPLATE_DB_USER_1;
import static monte.test.utils.EntityFactory.createApiUser;
import static monte.test.utils.EntityFactory.createDbUser;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceRetrieveSteps extends AbstractSteps {

    @Given("DB user")
    public void dbUser() throws Exception {
        var dbUser = mongoTemplate.save(createDbUser(TEMPLATE_DB_USER_1));
        testContext().setDbUser(dbUser);
    }

    @Given("DB size")
    public void currentDBSize() {
        var dbSize = mongoTemplate.getCollection("user").countDocuments();
        testContext().setDbSize(dbSize);
    }

    @Given("Api user")
    public void apiUser() throws IOException {
        var apiUser = createApiUser(TEMPLATE_API_USER_1, testContext().getDbUser().getId());
        testContext().setApiUser(apiUser);
    }

    @When("I send get all users request")
    public void getAllUsers() {
        var response = testUserApiClient.getAllUsers();
        testContext().setResponse(response);
    }

    @When("I send get user by id request")
    public void getUserById() {
        var userId = testContext().getDbUser().getId();
        var response = testUserApiClient.getUserById(userId);
        testContext().setResponse(response);
    }

    @Then("I validate that response body contains user")
    public void validateResponseBody() {
        var responseUserList = (List<TestApiUser>) testContext().getResponse().getBody();
        assertThat(responseUserList).as("Verify user")
                .contains(testContext().getApiUser());
    }

    @Then("I validate that response body size equals DB size")
    public void validateResponseBodySize() {
        var responseUserList = (List<TestApiUser>) testContext().getResponse().getBody();
        assertThat(responseUserList.size()).as("Verify db size")
                .isEqualTo(testContext().getDbSize());
    }

    @Then("I validate that response body equals user")
    public void validateResponseBodyEqualsUser() {
        var response = (TestApiUser) testContext().getResponse().getBody();
        assertThat(response).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .isEqualTo(testContext().getApiUser());
    }

}
