package banks.entities.bankAccount;

import org.junit.jupiter.api.Test;
import ru.itmo.application.entity.bankAccount.DebitBankAccount;
import ru.itmo.application.entity.bankAccount.IBankAccount;
import ru.itmo.application.entity.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DebitBankAccountTest {
    private final User user = new User("a", "b", "1", "1", "1");

    @Test
    public void percentsTest() {
        IBankAccount debitBankAccount = new DebitBankAccount(user, 1000L, 10);
        debitBankAccount.deposit(1000L);
        debitBankAccount.addDay();
        debitBankAccount.monthPay();
        assertEquals(1000 + 1000 * 10 / 100 /365, debitBankAccount.getBalance());
    }

    @Test
    public void checkLimitTest() {
        DebitBankAccount debitBankAccount = new DebitBankAccount(user, 20000L, 10);
        assertThrows(Exception.class, () -> debitBankAccount.withdraw(30000L));
    }

    @Test
    public void undoTransactionsTest() {
        DebitBankAccount debitBankAccount = new DebitBankAccount(user,
                1000L, 10);
        debitBankAccount.deposit(100L);
        debitBankAccount.deposit(200L);
        debitBankAccount.deposit(300L);
        debitBankAccount.undoTransaction(2);
        assertEquals(debitBankAccount.getBalance(), 300);
    }

}