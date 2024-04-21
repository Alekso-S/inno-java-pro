package ru.inno.dao;

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
public class UserDaoImpl implements UserDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    public UserDaoImpl(NamedParameterJdbcOperations jdbcOperations) {this.jdbcOperations = jdbcOperations;}

    @Override
    public void createTable() {
        String query = "CREATE TABLE users (id bigint AUTO_INCREMENT PRIMARY KEY, username varchar(255))";
        jdbcOperations.getJdbcOperations().execute(query);
    }

    @Override
    public User add(User user) {
        String query = "INSERT INTO users (username) VALUES (:username)";
        MapSqlParameterSource params = new MapSqlParameterSource("username", user.getUsername());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(query, params, keyHolder);
        return new User(Objects.requireNonNull(keyHolder.getKey()).longValue(), user.getUsername());
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT id, username FROM users ORDER BY id";
        return jdbcOperations.query(query, new UserMapper());
    }

    @Override
    public User getById(Long id) {
        String query = "SELECT id, username FROM users WHERE id=:id";
        Map<String, ?> params = Map.of("id", id);
        return jdbcOperations.queryForObject(query, params, new UserMapper());
    }

    @Override
    public void update(User user) {
        String query = "UPDATE users set username=:username WHERE id=:id";
        Map<String, ?> params = Map.of("username", user.getUsername(), "id", user.getId());
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
            return new User(rs.getLong("id"), rs.getString("username"));
        }
    }
}
