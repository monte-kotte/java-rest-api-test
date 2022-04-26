package monte.test.client;

import monte.test.model.api.TestApiUser;
import monte.test.model.api.error.TestValidationErrorResponse;
import monte.test.utils.ConfigFileReader;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TestUserApiClient {

    public static final String USER_SERVICE_URL = ConfigFileReader.getUserServiceUrl() + "/users";
    private final TestRestTemplate restTemplate;

    public TestUserApiClient(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<TestApiUser>> getAllUsers() {
        return restTemplate.exchange(USER_SERVICE_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
    }

    public ResponseEntity<TestApiUser> getUserById(String userId) {
        var url = USER_SERVICE_URL + "/" + userId;
        return restTemplate.getForEntity(url, TestApiUser.class);
    }

    public ResponseEntity<TestApiUser> postUser(TestApiUser apiUser) {
        var userEntity = new HttpEntity<>(apiUser);
        return restTemplate.postForEntity(USER_SERVICE_URL, userEntity, TestApiUser.class);
    }

    public ResponseEntity<TestValidationErrorResponse> postUserForError(TestApiUser apiUser) {
        var userEntity = new HttpEntity<>(apiUser);
        return restTemplate.postForEntity(USER_SERVICE_URL, userEntity, TestValidationErrorResponse.class);
    }

}
