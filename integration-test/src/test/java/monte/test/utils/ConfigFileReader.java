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

    private static String getPropertyFilePath() {
        var env = System.getProperty("env", DEFAULT_PROFILE);
        return PROPERTY_FILE_PATH_TEMPLATE.replace("${env}", env);
    }

}
