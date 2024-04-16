package ru.itmo.console;

import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.entity.user.UserBuilder;
import ru.itmo.application.service.ICentralBank;

import java.util.Scanner;
import java.util.UUID;

public class CreateNewUserScenario {
    private static final Scanner scanner = new Scanner(System.in);

    public static void run(ICentralBank centralBank) {
        System.out.println("Your bank: ");
        int bankId = scanner.nextInt();
        Bank bank = centralBank.getBank(UUID.fromString(String.valueOf(bankId)));

        System.out.println("Your name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.println("Your surname: ");
        String surname = scanner.nextLine();

        System.out.println("Your email: ");
        String email = scanner.nextLine();

        System.out.println("Your phone: ");
        String phone = scanner.nextLine();

        System.out.println("Your passport: ");
        String passport = scanner.nextLine();

        User client = new UserBuilder()
                .withName(name)
                .withSurname(surname)
                .withEmail(email)
                .withPhone(phone)
                .withPassport(passport)
                .build();

        System.out.println("Your id: " + client.getId());
        bank.addClient(client);
    }
}
