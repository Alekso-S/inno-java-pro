package ru.inno.service;

import ru.inno.domain.User;

import java.util.List;

public interface UserService {

    void init();

    User add(User ivan);

    List<User> getAll();

    User getById(Long id);

    void update(User user3);

    void deleteById(Long id);
}
