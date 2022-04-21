package monte.test.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class ConfigFileReader {

    private static Properties properties = new Properties();
    public static final String DEFAULT_VALUE = "";
    public static final String DEFAULT_PROFILE = "local";

    private static final String PROPERTY_FILE_PATH_TEMPLATE = "src/test/resources/test-${env}.properties";
    private static final String USER_SERVICE_URL_PROPERTY = "service.user.url";
    private static final String MONGODB_HOST_PROPERTY = "mongodb.host";
    private static final String MONGODB_PORT_PROPERTY = "mongodb.port";
    private static final String MONGODB_AUTHENTICATION_DB_PROPERTY = "mongodb.authentication-database";
    private static final String MONGODB_USERNAME_PROPERTY = "mongodb.username";
    private static final String MONGODB_PASSWORD_PROPERTY = "mongodb.password";
    private static final String MONGODB_DATABASE_PROPERTY = "mongodb.database";

    static {
        try {
            var fileName = getPropertyFilePath();
            var reader = new BufferedReader(new FileReader(fileName));
            properties.load(reader);
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Property file could not be used");
        }
    }

    public static String getUserServiceUrl() {
        return properties.getProperty(USER_SERVICE_URL_PROPERTY, DEFAULT_VALUE);
    }

    public static String getMongoHost() {
        return properties.getProperty(MONGODB_HOST_PROPERTY, DEFAULT_VALUE);
    }

    public static String getMongoPort() {
        return properties.getProperty(MONGODB_PORT_PROPERTY, DEFAULT_VALUE);
    }

    public static String getMongoAuthenticationDb() {
        return properties.getProperty(MONGODB_AUTHENTICATION_DB_PROPERTY, DEFAULT_VALUE);
    }

    public static String getMongoUsername() {
        return properties.getProperty(MONGODB_USERNAME_PROPERTY, DEFAULT_VALUE);
    }

    public static String getMongoPassword() {
        return properties.getProperty(MONGODB_PASSWORD_PROPERTY, DEFAULT_VALUE);
    }

    public static String getMongoDbName() {
        return properties.getProperty(MONGODB_DATABASE_PROPERTY, DEFAULT_VALUE);
    }

    private static String getPropertyFilePath() {
        var env = System.getProperty("env", DEFAULT_PROFILE);
        return PROPERTY_FILE_PATH_TEMPLATE.replace("${env}", env);
    }

}
