package ru.inno.api.dto;

public class ApiResponseError {
    private String message;

    public ApiResponseError() {
    }

    public ApiResponseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
