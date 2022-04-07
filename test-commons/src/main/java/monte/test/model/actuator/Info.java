package monte.test.model.actuator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Info {

    private Application app;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Application {
        private String version;
    }

}
