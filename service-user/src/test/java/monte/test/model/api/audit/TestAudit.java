package monte.test.model.api.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestAudit {
    String id;
    String entityName;
    String entityId;
    TestAction action;
    String comment;

    public enum TestAction {
        CREATE, READ, UPDATE, DELETE
    }

}
