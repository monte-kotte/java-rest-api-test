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
@AutoConfigureWireMock(port = 0)
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

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
