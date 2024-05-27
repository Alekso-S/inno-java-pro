package ru.inno.service;

import ru.inno.domain.Product;

import java.util.List;

public interface ProductService {
    Product add(Product product);

    List<Product> findAll();

    Product getById(Long id);

    void update(Product product);

    void deleteById(Long id);

    List<Product> findAllByUserId(Long userId);
}
