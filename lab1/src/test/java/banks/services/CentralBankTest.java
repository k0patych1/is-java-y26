package banks.services;

import org.junit.jupiter.api.Test;
import ru.itmo.application.entity.bank.Bank;
import ru.itmo.application.entity.user.User;
import ru.itmo.application.exception.DepositIsClosedNowException;
import ru.itmo.application.model.BankFinancialTerms;
import ru.itmo.application.service.CentralBank;
import ru.itmo.application.service.ICentralBank;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CentralBankTest {

    private final Bank bank;

    private final User user;

    private final UUID depositId;

    private final Bank bank2;

    private final User user2;

    private final UUID depositId2;
    public CentralBankTest() {
        user = new User("a", "y", "sdadsad", "777", "666");
        TreeMap<Long, Integer> treeMap = new TreeMap<>();
        treeMap.put(100L, 10);
        bank = new Bank("myBank",
                new BankFinancialTerms(10, 10L, 1000L, treeMap));
        bank.addClient(user);
        depositId = bank.createDepositAccount(user.getId(), 1000L, LocalDateTime.now().plusMonths(2));

        bank2 = new Bank("myBank2",
                new BankFinancialTerms(10, 10L, 1000L, treeMap));
        user2 = new User("a", "b", "sfsdfs", "34534543", "555");
        bank2.addClient(user2);
        depositId2 = bank2.createDebitAccount(user2.getId());
        bank2.getAccount(depositId2).deposit(1000L);
    }

    @Test
    public void addDayTest() {
        ICentralBank centralBank = CentralBank.getInstance();
        centralBank.registerBank(bank);
        assertThrows(DepositIsClosedNowException.class, () -> bank.getAccount(depositId).withdraw(100L));
        centralBank.addDays(80);
        assertDoesNotThrow(() -> bank.getAccount(depositId).withdraw(100L));
    }

    @Test
    public void transferTest() {
        CentralBank centralBank = CentralBank.getInstance();
        centralBank.registerBank(bank2);
        centralBank.registerBank(bank);

        centralBank.transferFromOneBankToAnother(bank2.getId(), bank.getId(), depositId2, depositId, 100L);

        assertEquals(bank2.getAccount(depositId2).getBalance(), 900);
        assertEquals(bank.getAccount(depositId).getBalance(), 1100);
    }

}