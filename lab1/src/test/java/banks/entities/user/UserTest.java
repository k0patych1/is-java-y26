package banks.entities.user;


import org.junit.jupiter.api.Test;
import ru.itmo.application.entity.user.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void userVerifiedTest() {
        assertThrows(Exception.class, () ->
                new User("name", "surname", null, null, "2"));
        User user = new User("name", "surname", "sfd", "1", "2");
        assertTrue(user.isVerified());
        user.setPassport(null);
        assertFalse(user.isVerified());
    }

    @Test
    public void receiveMessageTest() {
        User user = new User("name", "surname", "1", "2", "2");

        user.receiveMessage("message");
        assertFalse(user.getNotifications().isEmpty());
        assertEquals(user.getNotifications().get(0), "message");
    }
}