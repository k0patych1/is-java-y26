package ru.itmo.application.exception;

public class DepositIsClosedNowException extends RuntimeException {
    public DepositIsClosedNowException(String message) {
        super(message);
    }
}
