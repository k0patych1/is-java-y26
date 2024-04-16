package ru.itmo.application.entity.bankAccount;

import ru.itmo.application.entity.user.User;
import ru.itmo.application.model.AccountType;

import java.util.UUID;

public interface IBankAccount {
    AccountType getAccountType();

    UUID getId();

    Long getBalance();

    User getUser();

    void withdraw(Long amount);

    void deposit(Long amount);

    void transfer(IBankAccount account, Long amount);

    void undoTransaction(int number);

    void addDay();

    void monthPay();

    void setLimit(Long limit);
}
