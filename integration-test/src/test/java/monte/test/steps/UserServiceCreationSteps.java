package monte.test.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import monte.test.model.api.TestApiUser;
import monte.test.utils.ConfigFileReader;
import monte.test.utils.EntityFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static monte.test.utils.Constants.Files.TEMPLATE_API_USER_1;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceCreationSteps {

    TestRestTemplate restTemplate = new TestRestTemplate();
    ResponseEntity<TestApiUser> response;
    TestApiUser apiUser;

    @Given("Api user")
    public void createApiUser() throws Exception {
        apiUser = EntityFactory.createApiUser(TEMPLATE_API_USER_1);
    }

    @Given("Api user with valid overridden field")
    public void createApiUserWithFieldFromTable(DataTable dataTable) throws Exception {
        Map<String, Object> rows = dataTable.asMap(String.class, Object.class);
        apiUser = EntityFactory.createApiUser(TEMPLATE_API_USER_1);
        PropertyUtils.setNestedProperty(apiUser, (String) rows.get("fieldName"), rows.get("fieldValue"));
    }

    @When("I send post request")
    public void sendPostRequest() {
        var url = ConfigFileReader.getUserServiceUrl() + "/users";
        var userEntity = new HttpEntity<>(apiUser);
        response = restTemplate.postForEntity(url, userEntity, TestApiUser.class);
    }

    @Then("I validate that response code is {int}")
    public void validateResponseCode(int statusCodeValue) {
        assertThat(response.getStatusCodeValue()).as("Verify status code")
                .isEqualTo(statusCodeValue);
    }

    @Then("I validate that response body contains user")
    public void validateResponseBody() {
        assertThat(response.getBody()).as("Verify response")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields("id")
                .isEqualTo(apiUser);
        assertThat(response.getBody().getId()).isNotNull();
    }

}
