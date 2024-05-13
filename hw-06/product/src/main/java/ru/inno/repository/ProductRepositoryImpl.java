package ru.inno.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.inno.domain.Product;
import ru.inno.enums.ProductType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public ProductRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {this.jdbcOperations = jdbcOperations;}

    @PostConstruct
    private void createTable() {
        String query = "" +
                "CREATE TABLE products ( " +
                "       id          bigint          AUTO_INCREMENT PRIMARY KEY " +
                "   ,   user_id     bigint " +
                "   ,   number      varchar(255) " +
                "   ,   balance     decimal " +
                "   ,   type        varchar(255) " +
                ")";
        jdbcOperations.getJdbcOperations().execute(query);
    }

    @Override
    public Product add(Product product) {
        String query = "INSERT INTO products (user_id, number, balance, type) VALUES (:userId, :number, :balance, :type)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", product.getUserId())
                .addValue("number", product.getNumber())
                .addValue("balance", product.getBalance())
                .addValue("type", product.getType().name());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(query, params, keyHolder);
        return new Product(
                Objects.requireNonNull(keyHolder.getKey()).longValue(),
                product.getUserId(),
                product.getNumber(),
                product.getBalance(),
                product.getType()
        );
    }

    @Override
    public List<Product> findAll() {
        String query = "SELECT id, user_id, number, balance, type FROM products";
        return jdbcOperations.query(query, new ProductMapper());
    }

    @Override
    public List<Product> findAllByUserId(Long userId) {
        String query = "SELECT id, user_id, number, balance, type FROM products WHERE user_id=:userId";
        Map<String, ?> params = Map.of("userId", userId);
        return jdbcOperations.query(query, params, new ProductMapper());
    }

    @Override
    public Product getById(Long id) {
        String query = "SELECT id, user_id, number, balance, type FROM products WHERE id=:id";
        Map<String, ?> params = Map.of("id", id);
        return jdbcOperations.queryForObject(query, params, new ProductMapper());
    }

    @Override
    public void update(Product product) {
        String query = "" +
                "UPDATE     products " +
                "SET        user_id     = :userId " +
                "       ,   number      = :number " +
                "       ,   balance     = :balance " +
                "       ,   type        = :type " +
                "WHERE      id=:id";
        Map<String, ?> params = Map.of(
                "userId", product.getUserId(),
                "number", product.getNumber(),
                "balance", product.getBalance(),
                "type", product.getType().name(),
                "id", product.getId()
        );
        jdbcOperations.update(query, params);
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM products WHERE id=:id";
        Map<String, ?> params = Map.of("id", id);
        jdbcOperations.update(query, params);
    }

    private static class ProductMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int i) throws SQLException {
            return new Product(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getString("number"),
                    rs.getBigDecimal("balance"),
                    ProductType.valueOf(rs.getString("type"))
            );
        }
    }
}
