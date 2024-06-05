package ru.inno.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ApiPaymentDto {
    @NotNull
    private UUID uid;
    @NotNull
    private Long userId;
    @NotNull
    private BigDecimal amount;
}
