package ru.inno.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.domain.Product;
import ru.inno.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final PaymentService service;

    public ProductController(PaymentService service) {this.service = service;}

    @GetMapping
    public List<Product> findAllByUserId(@RequestParam("userId") Long userId) {
        return service.findAllByUserId(userId);
    }
}
