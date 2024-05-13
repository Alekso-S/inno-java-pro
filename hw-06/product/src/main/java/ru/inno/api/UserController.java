package ru.inno.api;

import org.springframework.web.bind.annotation.*;
import ru.inno.domain.User;
import ru.inno.service.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {this.service = service;}

    @PostMapping
    public User add(@RequestBody User user) {
        return service.add(user);
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "{id}")
    public User getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @PutMapping
    public void update(@RequestBody User user) {
        service.update(user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
