package monte.test.steps;


import monte.test.context.TestContext;
import monte.test.utils.ComponentFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;

import static monte.test.context.TestContext.CONTEXT;

public abstract class AbstractSteps {

    TestRestTemplate restTemplate = ComponentFactory.createTestRestTemplate();
    MongoTemplate mongoTemplate = ComponentFactory.createMongoTemplate();

    public TestContext testContext() {
        return CONTEXT;
    }

}
