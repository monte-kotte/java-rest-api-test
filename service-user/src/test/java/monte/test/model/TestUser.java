package monte.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestUser {
    String id;
    String firstName;
    String secondName;
    LocalDate dateOfBirth;
    String email;
    TestDL driverLicense;
    List<TestAddress> addresses;
}
