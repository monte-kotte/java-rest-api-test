package monte.api.model.user;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String id;
    String firstName;
    String secondName;
    LocalDate dateOfBirth;
    String email;
    DriverLicense driverLicense;
    List<Address> addresses;
}
