package monte.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import monte.service.user.UserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("component-test")
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractTest {

    @Container
    public static MongoDBContainer mongoDbContainer =
            new MongoDBContainer(DockerImageName.parse("mongo"));

    @DynamicPropertySource
    static void dynamicProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
    }

    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected ObjectMapper objectMapper;

}
