package ru.inno.dto;

import java.math.BigDecimal;

public class PaymentDto {
    long productId;
    BigDecimal amount;

    public PaymentDto(long productId, BigDecimal amount) {
        this.productId = productId;
        this.amount = amount;
    }

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
