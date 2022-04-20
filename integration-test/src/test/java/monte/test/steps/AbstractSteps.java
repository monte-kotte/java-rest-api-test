package monte.test.steps;

import monte.test.context.TestContext;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static monte.test.context.TestContext.CONTEXT;

public abstract class AbstractSteps {

    TestRestTemplate restTemplate = new TestRestTemplate();

    public TestContext testContext() {
        return CONTEXT;
    }

}
