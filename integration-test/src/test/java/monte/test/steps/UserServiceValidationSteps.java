package monte.test.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import monte.test.model.api.TestApiUser;
import monte.test.model.api.error.TestValidationErrorResponse;
import monte.test.model.api.error.TestViolation;
import monte.test.utils.ConfigFileReader;
import monte.test.utils.EntityFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static monte.test.utils.Constants.Files.TEMPLATE_API_USER_1;
import static monte.test.utils.CucumberValueUtil.STRING_VALUES;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceValidationSteps {

    TestRestTemplate restTemplate = new TestRestTemplate();
    ResponseEntity<TestValidationErrorResponse> errorResponse;
    TestApiUser apiUser;


    @Given("Api user with overridden field")
    public void createApiUserWithFieldFromTable(DataTable dataTable) throws Exception {
        Map<String, Object> rows = dataTable.asMap(String.class, Object.class);
        apiUser = EntityFactory.createApiUser(TEMPLATE_API_USER_1);
        PropertyUtils.setNestedProperty(apiUser, (String) rows.get("fieldName"), rows.get("fieldValue"));
    }

    @When("I send post request")
    public void sendPostRequest() {
        var url = ConfigFileReader.getUserServiceUrl() + "/users";
        var userEntity = new HttpEntity<>(apiUser);
        errorResponse = restTemplate.postForEntity(url, userEntity, TestValidationErrorResponse.class);
    }

    @Then("I validate that response contains violation ([\\s\\S]+) with ([\\s\\S]+)$")
    public void validateThatResponseContainsViolation(String fieldName, String violationMessage) {
        assertThat(errorResponse.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorResponse.getBody().getViolations()).as("Verify response")
                .containsExactlyInAnyOrder(TestViolation.builder()
                        .fieldName(fieldName)
                        .message(violationMessage)
                        .build());
    }

}
