package monte.test.context;

import monte.test.model.api.TestApiUser;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.lang.ThreadLocal.withInitial;

public enum TestContext {

    CONTEXT;

    private static final String API_USER = "API_USER";
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

    private <T> T get(String name) {
        return (T) testContexts.get()
                .get(name);
    }

    private <T> void set(String name, T object) {
        testContexts.get()
                .put(name, object);
    }

}
