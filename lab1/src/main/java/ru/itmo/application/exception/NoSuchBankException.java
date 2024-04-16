package ru.itmo.application.exception;

public class NoSuchBankException extends RuntimeException {
    public NoSuchBankException(String message) {
        super(message);
    }
}
