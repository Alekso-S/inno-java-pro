package ru.inno.service;

import ru.inno.dto.PaymentDto;
import ru.inno.model.Product;

import java.util.List;

public interface PaymentService {
    List<Product> findAllByUserId(Long userId);

    void execute(PaymentDto payment);
}
