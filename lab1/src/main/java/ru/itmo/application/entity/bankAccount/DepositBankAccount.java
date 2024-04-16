package ru.itmo.application.entity.bankAccount;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.exception.DepositIsClosedNowException;
import ru.itmo.application.exception.NotEnoughMoneyException;
import ru.itmo.application.exception.NotVerifiedAccountException;
import ru.itmo.application.model.AccountType;
import ru.itmo.application.model.Transaction;
import ru.itmo.application.model.TransactionType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepositBankAccount implements IBankAccount {
    @Getter
    private final UUID id;

    @Getter
    private final User user;

    private final LocalDateTime openTime;

    private LocalDateTime currentTime;

    @Getter
    private Long balance;

    @Setter
    private Integer percents;

    @Setter
    private Long limit;

    private final List<Transaction> transactions;

    private Long accumulatedPercents;

    public DepositBankAccount(User user, Long balance, LocalDateTime openTime, Long limit, Integer percents) {
        this.id = UUID.randomUUID();
        this.balance = balance;
        this.user = user;
        this.currentTime = LocalDateTime.now();
        this.openTime = openTime;
        this.limit = limit;
        this.transactions = new ArrayList<>();
        this.accumulatedPercents = 0L;
        this.percents = percents;
    }

    /**
     * @return account type
     */
    @Override
    public AccountType getAccountType() {
        return AccountType.Deposit;
    }

    /**
     * @param amount amount of money to withdraw
     */
    @Override
    public void withdraw(Long amount) {
        checkTransfer(amount);

        balance -= amount;
        transactions.add(new Transaction(TransactionType.WITHDRAW, amount, null));
    }

    /**
     * @param amount amount of money to deposit
     */
    @Override
    public void deposit(Long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        balance += amount;
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, null));
    }

    /**
     * @param account account to transfer money
     * @param amount amount of money to transfer
     */
    @Override
    public void transfer(IBankAccount account, Long amount) {
        checkTransfer(amount);

        balance -= amount;
        account.deposit(amount);
        transactions.add(new Transaction(TransactionType.TRANSFER, amount, account));
    }

    /**
     * @param number number of transaction to undo
     */
    @Override
    public void undoTransaction(int number) {
        Transaction transaction = transactions.get(number);
        switch (transaction.type()) {
            case TransactionType.DEPOSIT:
                balance -= transaction.amount();
                break;
            case TransactionType.WITHDRAW:
                balance += transaction.amount();
                break;
            case TransactionType.TRANSFER:
                balance += transaction.amount();
                IBankAccount account = transaction.whereTo();
                account.withdraw(transaction.amount());
                break;
        }
        transactions.remove(number);
    }

    @Override
    public void addDay() {
        accumulatedPercents += balance * percents / 100 / 365;
        currentTime = currentTime.plusDays(1);
    }

    @Override
    public void monthPay() {
        balance += accumulatedPercents;
        accumulatedPercents = 0L;
    }

    /**
     * @param amount amount of money to check
     */
    private void checkTransfer(Long amount) {
        if (currentTime.isBefore(openTime)) {
            throw new DepositIsClosedNowException("You can`t use deposit until it is opened");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (!user.isVerified() && amount > limit) {
            throw new NotVerifiedAccountException("User with id = " + user.getId() + " is not verified");
        }

        if (balance < amount) {
            throw new NotEnoughMoneyException("Not enough money on account with id = " + id);
        }
    }
}
