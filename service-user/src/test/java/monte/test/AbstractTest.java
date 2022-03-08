package monte.test;

import monte.service.user.UserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("component-test")
@AutoConfigureWireMock(port = 9999)
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

    public static final String TEMPLATE_DB_USER_1 = "src/test/resources/json/template-db-user1.json";
    public static final String TEMPLATE_API_USER_1 = "src/test/resources/json/template-api-user-1.json";
    public static final String TEMPLATE_API_USER_2 = "src/test/resources/json/template-api-user-2.json";
    public static final String TEMPLATE_API_AUDIT_1 = "src/test/resources/json/template-api-audit-1.json";
    public static final String TEMPLATE_API_AUDIT_2 = "src/test/resources/json/template-api-audit-2.json";

    public static MongoDBContainer mongoDbContainer =
            new MongoDBContainer(DockerImageName.parse("mongo"));

    static {
        // https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/
        mongoDbContainer.start();
    }

    @DynamicPropertySource
    static void dynamicProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
    }

    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected MongoTemplate mongoTemplate;

}
