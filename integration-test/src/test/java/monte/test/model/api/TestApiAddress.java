package monte.test.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestApiAddress {
    private String streetAddress;
    private String apartmentNumber;
    private String city;
    private String state;
    private String zip;
}
