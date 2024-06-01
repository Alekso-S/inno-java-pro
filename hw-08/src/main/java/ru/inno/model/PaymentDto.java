package ru.inno.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PaymentDto {
    private UUID uid;
    private Long userId;
    private BigDecimal amount;
}
