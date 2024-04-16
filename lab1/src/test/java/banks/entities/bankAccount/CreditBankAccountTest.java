package banks.entities.bankAccount;

import org.junit.jupiter.api.Test;
import ru.itmo.application.entity.bankAccount.CreditBankAccount;
import ru.itmo.application.entity.bankAccount.IBankAccount;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.exception.NotVerifiedAccountException;

import static org.junit.jupiter.api.Assertions.*;

public class CreditBankAccountTest {
    private final User user = new User("a", "b", "1", "1", "1");

    @Test
    public void percentsTest() {
        IBankAccount creditBankAccount = new CreditBankAccount(user,
                1000L, 10L, 100L);
        creditBankAccount.withdraw(1001L);
        creditBankAccount.addDay();
        creditBankAccount.monthPay();
        assertEquals(creditBankAccount.getBalance(), -101L);
    }

    @Test
    public void checkLimitTest() {
        user.setPassport(null);
        assertFalse(user.isVerified());
        CreditBankAccount creditBankAccount = new CreditBankAccount(user,
                1000L, 10L, 20000L);
        assertThrows(NotVerifiedAccountException.class, () -> creditBankAccount.withdraw(100L));
    }
}