package banks.entities.banks;

import org.junit.jupiter.api.Test;
import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.entity.bankAccount.IBankAccount;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.model.AccountType;
import ru.itmo.application.model.BankFinancialTerms;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTest {
    private final User user = new User("a", "b", "1", "1", "1");
    private final User user1 = new User("a", "c", "1", "1", "1");
    private final User user2 = new User("a", "d", "1", "1", "1");

    private final Bank bank;

    public BankTest() {
        TreeMap<Long, Integer> treeMap = new TreeMap<>();
        treeMap.put(100L, 10);
        bank = new Bank("myBank",
                new BankFinancialTerms(10, 10L, 1000L, treeMap));
        bank.addClient(user);
        bank.addClient(user1);
        bank.addClient(user2);
    }

    @Test
    public void accountOpenTest() {
        UUID depositId = bank.createDepositAccount(user.getId(), 1000L, LocalDateTime.now().plusMonths(2));

        IBankAccount bankAccount = bank.getAccount(depositId);

        assertEquals(bankAccount.getBalance(), 1000);
        assertEquals(bankAccount.getAccountType(), AccountType.Deposit);
        assertEquals(bankAccount.getUser().getId(), user.getId());
    }

    @Test
    public void notificationTest() {
        bank.subscribeMail(user.getId());
        bank.subscribeMail(user1.getId());

        bank.changeCreditCommission(11L);
        var userMessage = user.getNotifications();
        var userMessage1 = user.getNotifications();

        assertEquals(userMessage, userMessage1);
        assertEquals(user2.getNotifications().size(), 0);
        var message = userMessage.get(0);
        assertEquals("Credit commission has been changed", message);
    }
}