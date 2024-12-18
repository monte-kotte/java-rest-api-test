package monte.test.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import monte.test.client.TestUserApiClient;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;

public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static TestUserApiClient createTestUserApiClient() {
        return new TestUserApiClient(new TestRestTemplate());
    }

    public static MongoTemplate createMongoTemplate() {
        return new MongoTemplate(createMongoClient(ConfigFileReader.getMongoDbConnectionString()), ConfigFileReader.getMongoDbName());
    }

    private static MongoClient createMongoClient(String connectionString) {
        return MongoClients.create(connectionString);
    }

}
