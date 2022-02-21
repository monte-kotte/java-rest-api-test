package monte.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    String streetAddress;
    String apartmentNumber;
    String city;
    String state;
    String zip;
}
