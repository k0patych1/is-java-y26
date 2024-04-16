package ru.itmo.application.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    @Getter
    private final UUID id  = UUID.randomUUID();

    @NotNull
    @Getter
    private final String name;

    @NotNull
    private final String surname;

    @Setter
    @Nullable
    private String email;

    @Setter
    @Nullable
    private String phone;

    @Setter
    @Nullable
    private String passport;

    @Getter
    private final List<String> notifications;

    public User(@NotNull String name,
                @NotNull String surname,
                @Nullable String email,
                @NotNull String phone,
                @NotNull String passport) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.passport = passport;
        this.notifications = new ArrayList<>();
    }

    /**
     * @param message message to receive
     */
    public void receiveMessage(String message) {
        notifications.add(message);
    }

    /**
     * @return is user verified
     */
    public boolean isVerified() {
        return phone != null && passport != null;
    }
}
