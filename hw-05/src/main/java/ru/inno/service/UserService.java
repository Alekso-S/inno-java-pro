package ru.inno.service;

import ru.inno.domain.User;

import java.util.List;

public interface UserService {
    User add(User ivan);

    List<User> findAll();

    User getById(Long id);

    void update(User user3);

    void deleteById(Long id);
}
