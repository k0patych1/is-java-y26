package ru.itmo.console;

import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.entity.bankAccount.IBankAccount;
import ru.itmo.application.service.ICentralBank;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SelectAccountScenario {

    private final static Scanner scanner = new Scanner(System.in);

    public static void run(ICentralBank centralBank, Bank bank, UUID userId) {
        List<IBankAccount> accountList = bank.bankAccountsOfUser(userId);
        System.out.println("Select your bank account: ");
        for (IBankAccount account : accountList) {
            System.out.println(account.getId() + " " + account.getAccountType() + " " + account.getBalance());
        }
        int accountId = scanner.nextInt();
        if (accountId == 0) {
            return;
        }
        IBankAccount bankAccount = bank.getAccount(UUID.fromString(String.valueOf(accountId)));
        TransactionAction.run(bank, bankAccount, centralBank);
    }
}
