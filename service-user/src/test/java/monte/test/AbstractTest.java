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
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.io.IOException;

@ActiveProfiles("component-test")
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

    public static final String TEMPLATE_DB_USER_1 = "src/test/resources/json/template-db-user1.json";
    public static final String TEMPLATE_API_USER_1 = "src/test/resources/json/template-api-user-1.json";
    public static final String TEMPLATE_API_USER_2 = "src/test/resources/json/template-api-user-2.json";

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
    protected ObjectMapper objectMapper;

    protected <T> T fromFile(String fileName, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File(fileName), clazz);
    }

}
