package monte.service.user.controller;


import lombok.RequiredArgsConstructor;
import monte.api.model.user.User;
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
        return repository.findById(id)
            .map(mapper::mapToApi)
            .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @PostMapping("/users")
    public User createNewUser(@Valid @RequestBody User user) {
        user.setId(null); // force generate new ID
        return Optional.of(user)
            .map(mapper::mapToDb)
            .map(repository::save)
            .map(mapper::mapToApi)
            .get();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(name = "id") String id, @Valid @RequestBody User user) {
        user.setId(id); // use id from path
        return Optional.of(user)
            .map(mapper::mapToDb)
            .map(repository::save)
            .map(mapper::mapToApi)
            .get();
    }

}
