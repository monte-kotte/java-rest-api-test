package monte.test.suite.negative;

import monte.api.model.error.Violation;
import monte.test.AbstractTest;
import monte.test.model.api.TestApiUser;
import monte.test.model.api.error.TestValidationErrorResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ValidationTest extends AbstractTest {

    static Stream<Arguments> testDataProvider() {
        return Stream.of(
                arguments("firstName", null, "must not be blank"),
                arguments("firstName", "", "must not be blank"),
                arguments("firstName", "   ", "must not be blank"),

                arguments("secondName", null, "must not be blank"),
                arguments("secondName", "", "must not be blank"),
                arguments("secondName", "   ", "must not be blank"),

                arguments("dateOfBirth", null, "must not be null"),
                arguments("dateOfBirth", "", "must not be null"),
                arguments("dateOfBirth", "   ", "must not be null"),
                arguments("dateOfBirth", "January 17, 2000", "must be well-formed"),
                arguments("dateOfBirth", "2000/1/17", "must be well-formed"),
                arguments("dateOfBirth", "17-01-2000", "must be well-formed"),
                arguments("dateOfBirth", "2000-12-32", "must be well-formed"),
                arguments("dateOfBirth", "2000-13-01", "must be well-formed"),
                arguments("dateOfBirth", "2222-01-17", "must be a past date"),

                arguments("email", null, "must not be empty"),
                arguments("email", "", "must not be empty"),
                arguments("email", "   ", "must be a well-formed email address"),
                arguments("email", "incorrect_email", "must be a well-formed email address"),

                arguments("driverLicense.number", null, "must not be blank"),
                arguments("driverLicense.number", "", "must not be blank"),
                arguments("driverLicense.number", "  ", "must not be blank"),
                arguments("driverLicense.active", null, "must not be null"),

                arguments("addresses", null, "must not be empty"),
                arguments("addresses", List.of(), "must not be empty"),
                arguments("addresses[0].streetAddress", null, "must not be blank"),
                arguments("addresses[0].streetAddress", "", "must not be blank"),
                arguments("addresses[0].streetAddress", "  ", "must not be blank"),
                arguments("addresses[0].city", null, "must not be blank"),
                arguments("addresses[0].city", "", "must not be blank"),
                arguments("addresses[0].city", "  ", "must not be blank"),
                arguments("addresses[0].state", null, "must not be blank"),
                arguments("addresses[0].state", "", "must not be blank"),
                arguments("addresses[0].state", "   ", "must not be blank"),
                arguments("addresses[0].zip", null, "must not be blank"),
                arguments("addresses[0].zip", "", "must not be blank"),
                arguments("addresses[0].zip", "   ", "must not be blank")
        );
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void createUser_FieldValidationTest(String fieldName, Object fieldValue, String violationMessage) throws Exception {
        var user = fromFile(TEMPLATE_API_USER_1, TestApiUser.class);
        PropertyUtils.setNestedProperty(user, fieldName, fieldValue);
        var expectedViolation = Violation.builder()
                .fieldName(fieldName)
                .message(violationMessage)
                .build();

        var response = restTemplate.postForEntity("/users", user, TestValidationErrorResponse.class);

        assertThat(response.getStatusCode()).as("Verify status code")
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getViolations()).as("Verify response")
                .containsExactlyInAnyOrder(expectedViolation);
    }

}
