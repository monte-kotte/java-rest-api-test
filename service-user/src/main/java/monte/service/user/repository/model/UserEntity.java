package monte.service.user.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class UserEntity {
    @Id
    String id;
    String firstName;
    String secondName;
    LocalDate dateOfBirth;
    String email;
    DriverLicenseEntity driverLicense;
    List<AddressEntity> addresses;
}
