package banks.entities.bankAccount;


import org.junit.jupiter.api.Test;
import ru.itmo.application.entity.bankAccount.DepositBankAccount;
import ru.itmo.application.entity.bankAccount.IBankAccount;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.exception.DepositIsClosedNowException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepositBankAccountTest {
    private final User user = new User("a", "b", "1", "1", "1");

    @Test
    public void openDateTest() {
        IBankAccount depositBankAccount = new DepositBankAccount(user,
                1000L, LocalDateTime.now().plusMonths(2), 20000L, 10);
        assertThrows(DepositIsClosedNowException.class, ()->depositBankAccount.withdraw(100L));
        for (int i = 0; i < 100; i++) {
            depositBankAccount.addDay();
        }
        assertDoesNotThrow(() -> depositBankAccount.withdraw(100L));
    }
}