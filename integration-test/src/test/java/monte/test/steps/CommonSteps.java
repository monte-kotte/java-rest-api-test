package monte.test.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import monte.test.utils.EntityFactory;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.Map;

import static monte.test.utils.Constants.Files.TEMPLATE_API_USER_1;
import static monte.test.utils.CucumberValueUtil.STRING_VALUES;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps extends AbstractSteps {

    @DataTableType
    public Object objectType(String cell) {
        return STRING_VALUES.getOrDefault(cell, cell);
    }

    @Given("Api user with overridden field")
    public void createApiUserWithFieldFromTable(DataTable dataTable) throws Exception {
        Map<String, Object> rows = dataTable.asMap(String.class, Object.class);
        var apiUser = EntityFactory.createApiUser(TEMPLATE_API_USER_1);
        PropertyUtils.setNestedProperty(apiUser, (String) rows.get("fieldName"), rows.get("fieldValue"));
        testContext().setApiUser(apiUser);
    }

    @Then("I validate that response code is {int}")
    public void validateResponseCode(int statusCodeValue) {
        assertThat(testContext().getResponse().getStatusCodeValue()).as("Verify status code")
                .isEqualTo(statusCodeValue);
    }

}
