package ru.inno.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.inno.api.dto.ApiResponseError;
import ru.inno.entity.User;
import ru.inno.exception.UserNotFoundEx;
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

    @ExceptionHandler(UserNotFoundEx.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponseError handle404Error(RuntimeException e) {
        return new ApiResponseError(e.getMessage());
    }
}
