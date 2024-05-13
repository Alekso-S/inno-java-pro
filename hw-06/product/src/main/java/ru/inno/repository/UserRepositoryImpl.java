package ru.inno.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.inno.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public UserRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {this.jdbcOperations = jdbcOperations;}

    @PostConstruct
    private void createTable() {
        String query = "CREATE TABLE users (id bigint AUTO_INCREMENT PRIMARY KEY, name varchar(255))";
        jdbcOperations.getJdbcOperations().execute(query);
    }

    @Override
    public User add(User user) {
        String query = "INSERT INTO users (name) VALUES (:name)";
        MapSqlParameterSource params = new MapSqlParameterSource("name", user.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(query, params, keyHolder);
        return new User(Objects.requireNonNull(keyHolder.getKey()).longValue(), user.getName());
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT id, name FROM users ORDER BY id";
        return jdbcOperations.query(query, new UserMapper());
    }

    @Override
    public User getById(Long id) {
        String query = "SELECT id, name FROM users WHERE id=:id";
        Map<String, ?> params = Map.of("id", id);
        return jdbcOperations.queryForObject(query, params, new UserMapper());
    }

    @Override
    public void update(User user) {
        String query = "UPDATE users set name=:name WHERE id=:id";
        Map<String, ?> params = Map.of("name", user.getName(), "id", user.getId());
        jdbcOperations.update(query, params);
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM users WHERE id=:id";
        Map<String, ?> params = Map.of("id", id);
        jdbcOperations.update(query, params);
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("name"));
        }
    }
}
