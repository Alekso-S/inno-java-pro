package ru.inno.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDto {
    long productId;
    BigDecimal amount;

    public PaymentDto(long productId, BigDecimal amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
