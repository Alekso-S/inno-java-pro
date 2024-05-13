package ru.inno.repository;

import ru.inno.domain.User;

import java.util.List;

public interface UserRepository {
    User add(User user);

    List<User> findAll();

    User getById(Long id);

    void update(User user);

    void deleteById(Long id);
}
