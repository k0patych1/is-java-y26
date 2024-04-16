package ru.itmo.application.model;

import ru.itmo.application.entity.bankAccount.IBankAccount;

public record Transaction(TransactionType type, Long amount, IBankAccount whereTo) {
}