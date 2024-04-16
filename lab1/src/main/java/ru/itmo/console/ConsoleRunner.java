package ru.itmo.console;

import ru.itmo.application.service.ICentralBank;

import java.util.Scanner;

public class ConsoleRunner {

    private final static Scanner scanner = new Scanner(System.in);

    public static void run() {
        ICentralBank centralBank = Configuration.getConfiguration();
        while (true) {
            System.out.println("Enter your action:");
            System.out.println("1 - login as user");
            System.out.println("2 - create new user");
            System.out.println("3 - exit");
            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    UserLoginScenario.run(centralBank);
                    break;
                case 2:
                    CreateNewUserScenario.run(centralBank);
                    break;
                case 3:
                    return;
            }
        }
    }
}
