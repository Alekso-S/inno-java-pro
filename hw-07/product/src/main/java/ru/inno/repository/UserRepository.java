package ru.inno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inno.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
