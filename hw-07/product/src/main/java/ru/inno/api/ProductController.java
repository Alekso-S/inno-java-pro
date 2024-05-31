package ru.inno.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.inno.api.dto.ApiResponseError;
import ru.inno.entity.Product;
import ru.inno.exception.ProductNotFoundEx;
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

    @ExceptionHandler(ProductNotFoundEx.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponseError handle404Error(RuntimeException e) {
        return new ApiResponseError(e.getMessage());
    }
}
