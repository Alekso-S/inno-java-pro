package ru.inno.exception;

public class ProductNotFoundEx extends RuntimeException {
    public ProductNotFoundEx() {
        super("Product not found");
    }
}
