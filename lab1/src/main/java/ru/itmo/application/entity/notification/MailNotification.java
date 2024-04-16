package ru.itmo.application.entity.notification;


import ru.itmo.application.entity.user.User;

public class MailNotification implements INotification {
    private final User user;

    public MailNotification(User user) {
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
