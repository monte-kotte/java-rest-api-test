package monte.test.context;

import monte.test.model.api.TestApiUser;
import monte.test.model.db.TestDbUser;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.lang.ThreadLocal.withInitial;

public enum TestContext {

    CONTEXT;

    private static final String API_USER = "API_USER";
    private static final String DB_USER = "DB_USER";
    private static final String DB_SIZE = "DB_SIZE";
    private static final String RESPONSE = "RESPONSE";
    private final ThreadLocal<Map<String, Object>> testContexts = withInitial(HashMap::new);

    public ResponseEntity<Object> getResponse() {
        return get(RESPONSE);
    }

    public void setResponse(ResponseEntity response) {
        set(RESPONSE, response);
    }

    public TestApiUser getApiUser() {
        return get(API_USER);
    }

    public void setApiUser(TestApiUser apiUser) {
        set(API_USER, apiUser);
    }

    public TestDbUser getDbUser() {
        return get(DB_USER);
    }

    public void setDbUser(TestDbUser dbUser) {
        set(DB_USER, dbUser);
    }

    public long getDbSize() {
        return get(DB_SIZE);
    }

    public void setDbSize(long dbSize) {
        set(DB_SIZE, dbSize);
    }

    private <T> T get(String name) {
        return (T) testContexts.get()
                .get(name);
    }

    private <T> void set(String name, T object) {
        testContexts.get()
                .put(name, object);
    }

}
