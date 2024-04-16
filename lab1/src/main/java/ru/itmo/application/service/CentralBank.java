package ru.itmo.application.service;

import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.entity.bankAccount.IBankAccount;
import ru.itmo.application.exception.NoSuchBankException;
import ru.itmo.application.model.BankFinancialTerms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CentralBank implements ICentralBank {
    private static CentralBank instance;

    private final Map<UUID, Bank> banks;

    public CentralBank() {
        this.banks = new HashMap<>();
    }

    /**
     * @return instance of CentralBank
     */
    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }

    /**
     * @param bank bank to register
     * @return bank id
     */
    public UUID registerBank(Bank bank) {
        banks.put(bank.getId(), bank);
        return bank.getId();
    }

    /**
     * @param bankId bank id
     * @return bank
     */
    @Override
    public Bank getBank(UUID bankId) {
        return banks.get(bankId);
    }

    /**
     * @param bankFromId bank from id
     * @param bankToId bank to id
     * @param accountFromId account from id
     * @param accountToId account to id
     * @param amount amount to transfer
     */
    @Override
    public void transferFromOneBankToAnother(
            UUID bankFromId,
            UUID bankToId,
            UUID accountFromId,
            UUID accountToId,
            Long amount) {
        Bank bankFrom = banks.get(bankFromId);
        Bank bankTo = banks.get(bankToId);

        if (bankFrom == null) {
            throw new NoSuchBankException("Bank with id " + bankFromId + " not found");
        }

        if (bankTo == null) {
            throw new NoSuchBankException("Bank with id " + bankToId + " not found");
        }

        IBankAccount bankAccountFrom = bankFrom.getAccount(accountFromId);
        IBankAccount bankAccountTo = bankTo.getAccount(accountToId);

        bankAccountFrom.withdraw(amount);
        bankAccountTo.deposit(amount);
    }

    @Override
    public void monthPay() {
        banks.values()
                .forEach(Bank::monthPay);
    }

    @Override
    public void addDay() {
        banks.values()
                .forEach(Bank::addDay);
    }

    /**
     * @param day day to add
     */
    @Override
    public void addDays(int day) {
        for (int i = 0; i < day; i++) {
            addDay();
        }
    }
}
