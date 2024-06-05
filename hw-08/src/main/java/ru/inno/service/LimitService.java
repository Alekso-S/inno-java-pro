package ru.inno.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.inno.prop.AppProps;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class LimitService {

    private final AppProps appProps;
    private final PaymentService paymentService;

    public BigDecimal getByUserId(Long userId) {
        return appProps.getDefaultLimit().subtract(paymentService.sumByUserId(userId));
    }
}
