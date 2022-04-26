package monte.test.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import monte.test.model.api.TestApiUser;
import monte.test.utils.EntityFactory;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.Map;

import static monte.test.utils.CucumberValueUtil.STRING_VALUES;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps extends AbstractSteps {

    @DataTableType
    public Object objectType(String cell) {
        return STRING_VALUES.getOrDefault(cell, cell);
    }

    @Given("Api user from {string} with overridden field")
    public void createApiUserWithFieldFromTable(String templateName, DataTable dataTable) throws Exception {
        Map<String, Object> rows = dataTable.asMap(String.class, Object.class);
        var apiUser = EntityFactory.createApiUser(toFilePath(templateName));
        PropertyUtils.setNestedProperty(apiUser, (String) rows.get("fieldName"), rows.get("fieldValue"));
        testContext().setApiUser(apiUser);
    }

    @Then("I validate that response code is {int}")
    public void validateResponseCode(int statusCodeValue) {
        assertThat(testContext().getResponse().getStatusCodeValue()).as("Verify status code")
                .isEqualTo(statusCodeValue);
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
