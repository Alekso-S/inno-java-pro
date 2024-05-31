package ru.inno.model;

import lombok.Data;
import ru.inno.enums.ProductType;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;
    private Long userId;

    private String number;

    private BigDecimal balance;
    private ProductType type;
}
