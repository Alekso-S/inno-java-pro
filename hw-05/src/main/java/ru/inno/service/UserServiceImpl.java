package ru.inno.service;

import org.springframework.stereotype.Service;
import ru.inno.domain.User;
import ru.inno.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {this.repository = repository;}

    @Override
    public User add(User user) {
        return repository.add(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void update(User user) {
        repository.update(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
