package ru.itmo.application.entity.notification;

import ru.itmo.application.entity.user.User;

public class SmsNotification implements INotification {
    private final User user;

    public SmsNotification(User user) {
        this.user = user;
    }

    /**
     * @param message message to send
     */
    @Override
    public void sendMessage(String message) {
        user.receiveMessage(message);
    }
}
