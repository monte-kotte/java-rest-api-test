package monte.test.utils;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import monte.test.client.TestUserApiClient;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

public class ComponentFactory {

    public static TestUserApiClient createTestUserApiClient() {
        return new TestUserApiClient(new TestRestTemplate());
    }

    public static MongoTemplate createMongoTemplate() {
        var serverAddress = createServerAddress();
        var credential = createMongoCredential();
        return new MongoTemplate(createMongoClient(serverAddress, credential), ConfigFileReader.getMongoDbName());
    }

    private static ServerAddress createServerAddress() {
        return new ServerAddress(ConfigFileReader.getMongoHost(),
                Integer.parseInt(ConfigFileReader.getMongoPort()));
    }

    private static MongoCredential createMongoCredential() {
        return MongoCredential.createCredential(
                ConfigFileReader.getMongoUsername(),
                ConfigFileReader.getMongoAuthenticationDb(),
                ConfigFileReader.getMongoPassword().toCharArray());
    }

    private static MongoClient createMongoClient(ServerAddress serverAddress, MongoCredential credential) {
        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(serverAddress)))
                        .credential(credential)
                        .build());
    }

}
