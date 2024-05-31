package ru.inno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inno.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUserId(Long userId);
}
