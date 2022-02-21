package monte.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestAddress {
    String streetAddress;
    String apartmentNumber;
    String city;
    String state;
    String zip;
}
