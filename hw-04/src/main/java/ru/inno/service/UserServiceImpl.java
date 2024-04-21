package ru.inno.service;

import org.springframework.stereotype.Service;
import ru.inno.dao.UserDao;
import ru.inno.domain.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {this.userDao = userDao;}

    @Override
    public void init() {
        userDao.createTable();
    }

    @Override
    public User add(User ivan) {
        return userDao.add(ivan);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public void update(User user3) {
        userDao.update(user3);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
