package ru.inno.dao;

import ru.inno.domain.User;

import java.util.List;

public interface UserDao {
    void createTable();

    User add(User user);

    List<User> getAll();

    User getById(Long id);

    void update(User user);

    void deleteById(Long id);
}
