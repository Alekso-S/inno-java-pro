package ru.inno.service;

import org.springframework.stereotype.Service;
import ru.inno.entity.Product;
import ru.inno.exception.ProductNotFoundEx;
import ru.inno.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {this.repository = repository;}

    @Override
    public Product add(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(ProductNotFoundEx::new);
    }

    @Override
    public void update(Product product) {
        repository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
