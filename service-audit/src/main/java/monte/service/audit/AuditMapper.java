package monte.service.audit;

import monte.api.model.audit.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    Audit mapToApi(AuditEntity dbEntity);

    AuditEntity mapToDb(Audit apiEntity);
}
