package ru.itmo.application.entity.bank;

import lombok.Getter;
import ru.itmo.application.entity.bankAccount.CreditBankAccount;
import ru.itmo.application.entity.bankAccount.DebitBankAccount;
import ru.itmo.application.entity.bankAccount.DepositBankAccount;
import ru.itmo.application.entity.bankAccount.IBankAccount;
import ru.itmo.application.entity.notification.INotification;
import ru.itmo.application.entity.notification.MailNotification;
import ru.itmo.application.entity.notification.SmsNotification;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.model.AccountType;
import ru.itmo.application.model.BankFinancialTerms;

import java.time.LocalDateTime;
import java.util.*;

public class Bank {
    @Getter
    private final UUID id;

    @Getter
    private final String name;

    private final Map<UUID, User> clients;

    private final Map<UUID, IBankAccount> accounts;

    private final Queue<INotification> notifications;

    private BankFinancialTerms financialTerms;

    /**
     * @param name Bank name
     * @param financialTerms Bank financial terms
     */
    public Bank(String name,
                BankFinancialTerms financialTerms) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.clients = new HashMap<>();
        this.accounts = new HashMap<>();
        this.notifications = new ArrayDeque<>();
        this.financialTerms = financialTerms;
    }

    /**
     * @param clientId User id
     * @param balance Account balance
     * @param openTime Account open time
     * @return id of created deposit account
     */
    public UUID createDepositAccount(UUID clientId, Long balance, LocalDateTime openTime) {
        User client = clients.get(clientId);
        Integer percents = financialTerms.depositPercents().get(financialTerms.depositPercents().lowerKey(balance));
        IBankAccount depositAccount = new DepositBankAccount(
                client,
                balance,
                openTime,
                financialTerms.unverifiedAccountLimit(),
                percents);
        accounts.put(depositAccount.getId(), depositAccount);

        return depositAccount.getId();
    }

    /**
     * @param clientId User id
     * @return id of created debit account
     */
    public UUID createDebitAccount(UUID clientId) {
        User client = clients.get(clientId);
        Integer percents = financialTerms.percentsForDebit();

        IBankAccount debitAccount = new DebitBankAccount(client, financialTerms.unverifiedAccountLimit(), percents);
        accounts.put(debitAccount.getId(), debitAccount);

        return debitAccount.getId();
    }

    /**
     * @param clientId User id
     * @param balance Account balance
     * @return id of created credit account
     */
    public UUID createCreditAccount(UUID clientId, Long balance) {
        User client = clients.get(clientId);
        IBankAccount creditAccount = new CreditBankAccount(client,
                balance,
                financialTerms.unverifiedAccountLimit(),
                financialTerms.creditCommission());
        accounts.put(creditAccount.getId(), creditAccount);

        return creditAccount.getId();
    }

    /**
     * @param client User to add
     */
    public void addClient(User client) {
        clients.put(client.getId(), client);
    }

    /**
     * @param clientId
     * @return User with given id
     */
    public User getClient(UUID clientId) {
        return clients.get(clientId);
    }

    /**
     * @param accountId Account id
     * @return Account with given id
     */
    public IBankAccount getAccount(UUID accountId) {
        return accounts.get(accountId);
    }

    /**
     * @param userId User id
     * @return List of user accounts
     */
    public List<IBankAccount> bankAccountsOfUser(UUID userId) {
        List<IBankAccount> userAccounts = new ArrayList<>();
        accounts.values()
                .stream()
                .filter(account -> account.getUser().getId().equals(userId))
                .forEach(userAccounts::add);
        return userAccounts;
    }

    public void monthPay() {
        accounts.values()
                .forEach(IBankAccount::monthPay);
    }

    public void addDay() {
        accounts.values()
                .forEach(IBankAccount::addDay);
    }

    /**
     * @param passport New passport
     * @param userId User id
     */
    public void changePassport(String passport, UUID userId) {
        User user = clients.get(userId);
        user.setPassport(passport);
    }

    /**
     * @param email New email
     * @param userId User id
     */
    public void changeEmail(String email, UUID userId) {
        User user = clients.get(userId);
        user.setEmail(email);
    }

    /**
     * @param phone New phone
     * @param userId User id
     */
    public void changePhone(String phone, UUID userId) {
        User user = clients.get(userId);
        user.setPhone(phone);
    }

    /**
     * @param userId User id
     */
    public void subscribeMail(UUID userId) {
        User user = clients.get(userId);
        notifications.add(new MailNotification(user));
    }

    /**
     * @param userId User id
     */
    public void subscribeSms(UUID userId) {
        User user = clients.get(userId);
        notifications.add(new SmsNotification(user));
    }

    /**
     * @param message Message to send
     */
    public void sendNotifications(String message) {
        notifications.forEach(notification -> notification.sendMessage(message));
    }

    /**
     * @param newPercents New percents for debit
     */
    public void changePercentsForDebit(int newPercents) {
        financialTerms = new BankFinancialTerms(
                newPercents,
                financialTerms.creditCommission(),
                financialTerms.unverifiedAccountLimit(),
                financialTerms.depositPercents());

        accounts.values()
                .stream()
                .filter(account -> account.getAccountType().equals(AccountType.Debit))
                .forEach(account -> ((DebitBankAccount) account).setPercents(newPercents));

        sendNotifications("Percents for debit have been changed");
    }

    /**
     * @param newCommission New commission for credit
     */
    public void changeCreditCommission(Long newCommission) {
        financialTerms = new BankFinancialTerms(
                financialTerms.percentsForDebit(),
                newCommission,
                financialTerms.unverifiedAccountLimit(),
                financialTerms.depositPercents());

        accounts.values()
                .stream()
                .filter(account -> account.getAccountType().equals(AccountType.Credit))
                .forEach(account -> ((CreditBankAccount) account).setCommission(newCommission));

        sendNotifications("Credit commission has been changed");
    }

    /**
     * @param newLimit New unverified account limit
     */
    public void changeUnverifiedAccountLimit(Long newLimit) {
        financialTerms = new BankFinancialTerms(
                financialTerms.percentsForDebit(),
                financialTerms.creditCommission(),
                newLimit,
                financialTerms.depositPercents());

        accounts.values()
                .forEach(account -> account.setLimit(newLimit));

        sendNotifications("Unverified account limit has been changed");
    }

    /**
     * @param newPercents New percents for deposit
     */
    public void changeDepositPercents(TreeMap<Long, Integer> newPercents) {
        financialTerms = new BankFinancialTerms(
                financialTerms.percentsForDebit(),
                financialTerms.creditCommission(),
                financialTerms.unverifiedAccountLimit(),
                newPercents);

        accounts.values()
                .stream()
                .filter(account -> account.getAccountType().equals(AccountType.Deposit))
                .forEach(account -> {
                    DepositBankAccount depositAccount = (DepositBankAccount) account;
                    Long balance = depositAccount.getBalance();
                    Integer percents = newPercents.get(newPercents.lowerKey(balance));
                    depositAccount.setPercents(percents);
                });

        sendNotifications("Deposit percents have been changed");
    }
}
