package monte.test.model.actuator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.actuate.health.Status;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Health {
    private Status status;
}
