package com.thesnoozingturtle.bloggingrestapi.exceptions;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
