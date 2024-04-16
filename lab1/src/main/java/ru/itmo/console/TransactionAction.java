package ru.itmo.console;

import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.entity.bankAccount.IBankAccount;
import ru.itmo.application.service.ICentralBank;

import java.util.Scanner;
import java.util.UUID;

public class TransactionAction {
    private static Scanner scanner = new Scanner(System.in);

    public static void run(Bank bank, IBankAccount bankAccount, ICentralBank centralBank) {
        while (true) {
            System.out.println("Choose action :");
            System.out.println("1 - transfer");
            System.out.println("2 - withdraw");
            System.out.println("3 - replenishment");
            System.out.println("4 - exit");
            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    System.out.println("Destination bank id: ");
                    String bankId = scanner.next();
                    UUID bankUuid = UUID.fromString(bankId);
                    System.out.println("Destination account id: ");
                    String accountId = scanner.next();
                    UUID accountUuid = UUID.fromString(accountId);
                    System.out.println("Money : ");
                    Long money = scanner.nextLong();
                    transfer(bank.getId(), bankUuid, bankAccount.getId(), accountUuid, money, centralBank);

                    break;
                case 2:
                    System.out.println("Money :");
                    withdraw(bankAccount, scanner.nextLong());
                    break;
                case 3:
                    System.out.println("Money: ");
                    deposit(bankAccount, scanner.nextLong());
                    break;
            }
        }
    }

    private static void transfer(UUID srcBankId,
                                 UUID dstBankId,
                                 UUID srcAccountId,
                                 UUID dstAccountId,
                                 Long money,
                                 ICentralBank centralBank) {
        centralBank.transferFromOneBankToAnother(srcBankId, dstBankId, srcAccountId, dstAccountId, money);
    }

    private static void withdraw(IBankAccount bankAccount, Long money) {
        bankAccount.withdraw(money);
    }

    private static void deposit(IBankAccount bankAccount, Long money) {
        bankAccount.deposit(money);
    }
}
