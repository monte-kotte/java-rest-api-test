package monte.service.user.repository.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    String dateOfBirth;
    String email;
    DriverLicenseEntity driverLicense;
    List<AddressEntity> addresses;
}
