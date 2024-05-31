package ru.inno.exception;

public class UserNotFoundEx extends RuntimeException {
    public UserNotFoundEx() {
        super("User not found");
    }
}
