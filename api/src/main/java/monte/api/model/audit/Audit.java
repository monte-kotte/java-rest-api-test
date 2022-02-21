package monte.api.model.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    String id;
    String entityName;
    String entityId;
    Action action;
    String comment;


    public enum Action {
        CREATE, READ, UPDATE, DELETE
    }
}
