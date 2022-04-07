package monte.test.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestDbAddress {
    private String streetAddress;
    private String apartmentNumber;
    private String city;
    private String state;
    private String zip;
}
