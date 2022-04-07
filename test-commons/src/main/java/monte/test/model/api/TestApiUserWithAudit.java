package monte.test.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import monte.test.model.api.audit.TestAudit;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestApiUserWithAudit {
    TestApiUser user;
    List<TestAudit> auditEvents;
}
