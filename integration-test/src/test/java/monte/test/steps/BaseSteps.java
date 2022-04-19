package monte.test.steps;

import io.cucumber.java.DataTableType;

import static monte.test.utils.CucumberValueUtil.STRING_VALUES;

public class BaseSteps {

    @DataTableType
    public Object objectType(String cell) {
        return STRING_VALUES.getOrDefault(cell, cell);
    }

}
