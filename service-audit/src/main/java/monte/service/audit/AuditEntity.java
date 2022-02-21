package monte.service.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("audit")
public class AuditEntity {
    @Id
    String id;
    String entityName;
    String entityId;
    String action;
    String comment;
}
