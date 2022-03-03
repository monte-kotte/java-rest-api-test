package monte.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotBlank
    String streetAddress;
    String apartmentNumber;
    @NotBlank
    String city;
    @NotBlank
    String state;
    @NotBlank
    String zip;
}
