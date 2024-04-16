package ru.itmo.console;

import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.service.ICentralBank;

import java.util.Scanner;
import java.util.UUID;

public class UserLoginScenario {
    private static Scanner scanner = new Scanner(System.in);
    public static void run(ICentralBank centralBank) {
        System.out.println("Your bank: ");
        String bankId = scanner.next();
        UUID bankUuid = UUID.fromString(bankId);
        System.out.println("Your id: ");
        String userId = scanner.next();
        UUID userUuid = UUID.fromString(userId);
        System.out.println("Your name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        Bank bank = centralBank.getBank(bankUuid);
        User user = bank.getClient(userUuid);
        if (!user.getName().equals(name)) {
            return;
        }
        System.out.println("1 - create bank account");
        System.out.println("2 - select bank account");
        int action = scanner.nextInt();
        switch (action) {
            case 1:
                CreateBankAccountScenario.run(bank, user.getId());
                break;
            case 2:
                SelectAccountScenario.run(centralBank, bank, user.getId());
                break;
        }

    }
}
