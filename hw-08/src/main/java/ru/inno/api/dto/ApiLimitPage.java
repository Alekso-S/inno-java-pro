package ru.inno.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ApiLimitPage {
    private Long userId;
    private BigDecimal limit;
}
