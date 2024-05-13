package ru.inno.api.dto;

import java.math.BigDecimal;

public class ApiPaymentDto {
    long productId;
    BigDecimal amount;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
