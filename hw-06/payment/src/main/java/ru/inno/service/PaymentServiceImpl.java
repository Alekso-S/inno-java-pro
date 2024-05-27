package ru.inno.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.inno.domain.Product;
import ru.inno.dto.PaymentDto;
import ru.inno.exception.InsufficientBalanceEx;
import ru.inno.exception.ProductNotFoundEx;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;

    public PaymentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<Product> findAllByUserId(Long userId) {
        return restTemplate.exchange("/products?userId={userId}", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Product>>() {}, Map.of("userId", userId))
                .getBody();
    }

    @Override
    public void execute(PaymentDto payment) {
        ResponseEntity<Product> entity = restTemplate.getForEntity("/products/{productId}", Product.class, Map.of("productId", payment.getProductId()));
        Product product = entity.getBody();
        if (product == null || product.getId() == null) {
            throw new ProductNotFoundEx();
        }
        if (product.getBalance().compareTo(payment.getAmount()) < 0) {
            throw new InsufficientBalanceEx();
        }
        product.setBalance(product.getBalance().subtract(payment.getAmount()));
        restTemplate.put("/products", product);
    }
}
