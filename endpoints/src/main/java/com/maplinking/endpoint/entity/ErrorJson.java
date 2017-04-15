package com.maplinking.endpoint.entity;

public class ErrorJson {

    private final String message;

    public ErrorJson(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorJson{" +
                "message='" + message + '\'' +
                '}';
    }
}
