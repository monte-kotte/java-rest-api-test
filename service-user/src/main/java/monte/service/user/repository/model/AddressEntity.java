package monte.service.user.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    String streetAddress;
    String apartmentNumber;
    String city;
    String state;
    String zip;
}
