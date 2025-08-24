package monte.api.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
