package monte.api.model.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    List<Violation> violations = new ArrayList<>();

    public void addViolation(Violation violation) {
        violations.add(violation);
    }

}
