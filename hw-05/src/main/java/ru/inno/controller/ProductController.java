package ru.inno.controller;

import org.springframework.web.bind.annotation.*;
import ru.inno.domain.Product;
import ru.inno.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {this.service = service;}

    @PostMapping
    public Product add(@RequestBody Product product) {
        return service.add(product);
    }

    @GetMapping
    public List<Product> findAllByUserId(@RequestParam("userId") Long userId) {
        return service.findAllByUserId(userId);
    }

    @GetMapping(path = "{id}")
    public Product getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @PutMapping
    public void update(@RequestBody Product product) {
        service.update(product);
    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
