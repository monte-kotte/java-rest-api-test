package monte.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    @NotNull
    @Pattern(regexp = "\\d{5}(\\-\\d{4})?", message = "must match format 'xxxxx' or 'xxxxx-xxxx'")
    String zip;
}
