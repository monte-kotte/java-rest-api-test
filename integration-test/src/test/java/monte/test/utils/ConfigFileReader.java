package monte.test.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private static Properties properties;
    private static final String PROPERTY_FILE_PATH= "src/test/resources/test-local.properties";
    private static final String USER_SERVICE_URL_PROPERTY = "service.user.url";

    static {
        try {
            var reader = new BufferedReader(new FileReader(PROPERTY_FILE_PATH));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Property file not found at " + PROPERTY_FILE_PATH);
        }
    }

    public static String getUserServiceUrl() {
        var url = properties.getProperty(USER_SERVICE_URL_PROPERTY);
        if(url != null) return url;
        else throw new RuntimeException("Url not specified in the property file " + PROPERTY_FILE_PATH);
    }

}
