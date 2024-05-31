package ru.inno.exception;

public class InsufficientBalanceEx extends RuntimeException {
    public InsufficientBalanceEx() {
        super("Insufficient balance");
    }
}
