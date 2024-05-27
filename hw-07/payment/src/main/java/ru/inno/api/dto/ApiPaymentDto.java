package ru.inno.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ApiPaymentDto {
    long productId;
    BigDecimal amount;
}
