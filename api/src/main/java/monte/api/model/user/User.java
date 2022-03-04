package monte.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String id;
    @NotBlank
    String firstName;
    @NotBlank
    String secondName;
    @Past
    @NotNull
    LocalDate dateOfBirth;
    @Email
    @NotEmpty
    String email;
    @Valid
    DriverLicense driverLicense;
    @Valid
    @NotEmpty
    List<Address> addresses;
}
