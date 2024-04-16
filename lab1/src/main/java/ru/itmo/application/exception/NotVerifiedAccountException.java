package ru.itmo.application.exception;

public class NotVerifiedAccountException extends RuntimeException {
    public NotVerifiedAccountException(String message) {
        super(message);
    }
}
