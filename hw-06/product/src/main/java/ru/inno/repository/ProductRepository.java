package ru.inno.repository;

import ru.inno.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product add(Product Product);

    List<Product> findAll();

    List<Product> findAllByUserId(Long userId);

    Product getById(Long id);

    void update(Product Product);

    void deleteById(Long id);
}
