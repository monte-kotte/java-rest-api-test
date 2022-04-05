package monte.test.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestApiUser {
    private String id;
    private String firstName;
    private String secondName;
    private String dateOfBirth;
    private String email;
    private TestApiDL driverLicense;
    private List<TestApiAddress> addresses;
}
