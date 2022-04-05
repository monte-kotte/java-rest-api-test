import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        glue = {"monte.test.steps"},
        plugin = {"pretty", "html:target/site/cucumber-pretty", "json:target/cucumber/cucumber.json",
                "json:target/cucumber-reports/cucumber.json", "pretty",
                "html:target/cucumber-reports/cucumber-reports.html"})
public class TestRunner {
}
