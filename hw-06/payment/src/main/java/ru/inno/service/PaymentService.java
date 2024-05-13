package ru.inno.service;

import ru.inno.domain.Product;
import ru.inno.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    List<Product> findAllByUserId(Long userId);

    void execute(PaymentDto payment);
}
