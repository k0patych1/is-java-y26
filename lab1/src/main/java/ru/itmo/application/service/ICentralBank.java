package ru.itmo.application.service;

import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.model.BankFinancialTerms;

import java.util.UUID;

public interface ICentralBank {
    UUID registerBank(Bank bank);

    Bank getBank(UUID bankId);

    void transferFromOneBankToAnother(UUID bankFromId,
                                      UUID bankToId,
                                      UUID accountFromId,
                                      UUID accountToId,
                                      Long amount);

    void monthPay();

    void addDay();

    void addDays(int day);
}
