package monte.test.steps;


import monte.test.client.TestUserApiClient;
import monte.test.context.TestContext;
import monte.test.utils.ComponentFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import static monte.test.context.TestContext.CONTEXT;

public abstract class AbstractSteps {

    protected TestUserApiClient testUserApiClient = ComponentFactory.createTestUserApiClient();
    protected MongoTemplate mongoTemplate = ComponentFactory.createMongoTemplate();

    public TestContext testContext() {
        return CONTEXT;
    }

}
