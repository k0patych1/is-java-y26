package ru.itmo.console;

import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.entity.user.UserBuilder;
import ru.itmo.application.model.BankFinancialTerms;
import ru.itmo.application.service.CentralBank;
import ru.itmo.application.service.ICentralBank;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.UUID;

public class Configuration {

    public static ICentralBank getConfiguration() {
        ICentralBank centralBank = CentralBank.getInstance();
        registerAlfaBank(centralBank);
        registerTinkoffBank(centralBank);

        return centralBank;
    }


    private static User createUserRuslan() {
        return new UserBuilder()
                .withName("Ruslan")
                .withSurname("Rafikov")
                .withEmail("rr@gmail.com")
                .withPhone("777")
                .withPassport("777")
                .build();
    }

    private static User createUserNikolai() {
        return new UserBuilder()
                .withName("Nikolai")
                .withSurname("Nick")
                .withEmail("nn@gmail.com")
                .withPhone("666")
                .withPassport("666")
                .build();
    }

    private static User createUserMikhail() {
        return new UserBuilder()
                .withName("Mikhail")
                .withSurname("Mik")
                .withEmail("mm@gmail.com")
                .withPhone("228")
                .withPassport("228")
                .build();
    }

    private static UUID registerTinkoffBank(ICentralBank centralBank) {
        BankFinancialTerms bankFinancialTerms = new BankFinancialTerms(
                10,
                100L,
                5000L,
                new TreeMap<>() {{
                    put(1000L, 5);
                    put(10000L, 10);
                    put(50000L, 15);
                }});

        Bank bank = new Bank("tinkoff", bankFinancialTerms);

        User ruslan = createUserRuslan();
        bank.addClient(ruslan);
        bank.createDebitAccount(ruslan.getId());


        User nikolai = createUserNikolai();
        bank.addClient(nikolai);
        bank.createDepositAccount(nikolai.getId(), 10000L, LocalDateTime.now().plusMonths(2));

        return centralBank.registerBank(bank);
    }


    private static UUID registerAlfaBank(ICentralBank centralBank) {
        BankFinancialTerms bankFinancialTerms = new BankFinancialTerms(
                10,
                100L,
                5000L,
                new TreeMap<>() {{
                    put(1000L, 5);
                    put(10000L, 10);
                    put(50000L, 15);
                }});

        Bank bank = new Bank("alfa", bankFinancialTerms);

        User mikhail = createUserMikhail();
        bank.addClient(mikhail);
        bank.createCreditAccount(mikhail.getId(), 10000L);

        return centralBank.registerBank(bank);
    }
}
