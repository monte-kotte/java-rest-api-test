package monte.api.model.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
