package ru.inno.domain;

import ru.inno.enums.ProductType;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private Long id;
    private Long userId;

    private String number;

    private BigDecimal balance;
    private ProductType type;

    public Product() {
    }

    public Product(Long id, Long userId, String number, BigDecimal balance, ProductType type) {
        this.id = id;
        this.userId = userId;
        this.number = number;
        this.balance = balance;
        this.type = type;
    }

    public Product(Long userId, String number, BigDecimal balance, ProductType type) {
        this.userId = userId;
        this.number = number;
        this.balance = balance;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Product product = (Product) object;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", userId=" + userId +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                '}';
    }
}
