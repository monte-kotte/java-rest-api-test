package monte.service.user.controller;


import lombok.RequiredArgsConstructor;
import monte.api.model.audit.Audit;
import monte.api.model.user.User;
import monte.service.user.client.AuditClient;
import monte.service.user.mapper.UserMapper;
import monte.service.user.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuditClient auditClient;
    private final UserRepository repository;
    private final UserMapper mapper;

    @GetMapping("/users")
    public List<User> getAll() {
        return repository.findAll().stream()
                .map(mapper::mapToApi)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable(name = "id") String id) {
        var user = repository.findById(id)
                .map(mapper::mapToApi)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        createAuditRecord(user, Audit.Action.READ, "read user");
        return user;
    }

    @PostMapping("/users")
    public User createNewUser(@Valid @RequestBody User user) {
        user.setId(null); // force generate new ID
        var userCreated = Optional.of(user)
                .map(mapper::mapToDb)
                .map(repository::save)
                .map(mapper::mapToApi)
                .get();
        createAuditRecord(userCreated, Audit.Action.CREATE, "create new user");
        return userCreated;
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(name = "id") String id, @Valid @RequestBody User user) {
        user.setId(id); // use id from path
        var userUpdated = Optional.of(user)
                .map(mapper::mapToDb)
                .map(repository::save)
                .map(mapper::mapToApi)
                .get();
        createAuditRecord(userUpdated, Audit.Action.UPDATE, "update user");
        return userUpdated;
    }

    private void createAuditRecord(User user, Audit.Action action, String comment) {
        auditClient.sendNotification(Audit.builder()
                .entityName(User.class.getSimpleName())
                .entityId(user.getId())
                .action(action)
                .comment(comment)
                .build());
    }

}
