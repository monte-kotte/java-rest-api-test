package monte.test.model.api.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import monte.api.model.error.Violation;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestValidationErrorResponse {
    List<Violation> violations = new ArrayList<>();
}
