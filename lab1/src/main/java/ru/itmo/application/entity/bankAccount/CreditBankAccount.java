package ru.itmo.application.entity.bankAccount;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.exception.NotVerifiedAccountException;
import ru.itmo.application.model.AccountType;
import ru.itmo.application.model.Transaction;
import ru.itmo.application.model.TransactionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreditBankAccount implements IBankAccount {
    @Getter
    private final UUID id;

    @Getter
    private final User user;

    @Getter
    private Long balance;

    @Setter
    private Long commission;

    private Long commissionPay;

    @Setter
    private Long limit;

    private final List<Transaction> transactions;

    public CreditBankAccount(User user, Long balance, Long limit, Long commission) {
        this.balance = balance;
        this.id = UUID.randomUUID();
        this.user = user;
        this.limit = limit;
        this.commission = commission;
        this.commissionPay = 0L;
        this.transactions = new ArrayList<>();
    }

    /**
     * @return account type
     */
    @Override
    public AccountType getAccountType() {
        return AccountType.Credit;
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
     * @param amount  amount of money to transfer
     */
    @Override
    public void transfer(IBankAccount account, Long amount) {
        checkTransfer(amount);

        balance -= amount;
        account.deposit(amount);
        transactions.add(new Transaction(TransactionType.TRANSFER, amount, account));
    }

    /**
     * @param amount amount of money to check
     */
    private void checkTransfer(Long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (!user.isVerified() && amount > limit) {
            throw new NotVerifiedAccountException("User with id = " + user.getId() + " is not verified");
        }
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
        if (balance < 0) {
            commissionPay += commission;
        }
    }

    @Override
    public void monthPay() {
        balance -= commissionPay;
        commissionPay = 0L;
    }
}
