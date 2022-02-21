package monte.service.audit;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import monte.api.model.audit.Audit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuditController {

    private final AuditMapper mapper;
    private final AuditRepository repository;

    @GetMapping("/audit-events")
    public List<Audit> getAll() {
        return repository.findAll().stream()
            .map(mapper::mapToApi)
            .collect(Collectors.toList());
    }

    @GetMapping("/audit-events/{id}")
    public Audit getById(@PathVariable(name = "id") String id) {
        return repository.findById(id)
            .map(mapper::mapToApi)
            .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @PostMapping("/audit-events")
    public Audit createNewEvent(@RequestBody Audit audit) {
        return Optional.of(audit)
            .map(mapper::mapToDb)
            .map(repository::save)
            .map(mapper::mapToApi)
            .get();
    }

}
