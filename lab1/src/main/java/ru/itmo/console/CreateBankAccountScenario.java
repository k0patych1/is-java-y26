package ru.itmo.console;

import ru.itmo.application.entity.bank.Bank;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class CreateBankAccountScenario {
    private final static Scanner scanner = new Scanner(System.in);

    public static void run(Bank bank, UUID clientId) {
        System.out.println("Choose account type: ");
        System.out.println("1 - Deposit");
        System.out.println("2 - Credit");
        System.out.println("3 - Debit");
        int accountType = scanner.nextInt();
        switch (accountType) {
            case 1:
                System.out.println("Enter deposit amount: ");
                long depositAmount = scanner.nextLong();
                System.out.println("Enter deposit months: ");
                int depositOpenMonths = scanner.nextInt();
                bank.createDepositAccount(clientId, depositAmount, LocalDateTime.now().plusMonths(depositOpenMonths));
                break;
            case 2:
                System.out.println("Enter credit amount: ");
                long creditAmount = scanner.nextLong();
                bank.createCreditAccount(clientId, creditAmount);
                break;
            case 3:
                bank.createDebitAccount(clientId);
                break;
        }
    }
}
