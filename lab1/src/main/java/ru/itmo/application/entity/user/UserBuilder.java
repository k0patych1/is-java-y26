package ru.itmo.application.entity.user;

import lombok.Builder;

public class UserBuilder {
    private String name;

    private String surname;

    private String email;

    private String phone;

    private String passport;

    /**
     * @param name name of user
     * @return UserBuilder class with name
     */
    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param surname surname of user
     * @return UserBuilder class with surname
     */
    public UserBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    /**
     * @param email email of user
     * @return UserBuilder class with email
     */
    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @param phone phone of user
     * @return UserBuilder class with phone
     */
    public UserBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * @param passport passport of user
     * @return UserBuilder class with passport
     */
    public UserBuilder withPassport(String passport) {
        this.passport = passport;
        return this;
    }

    /**
     * @return new user
     */
    public User build() {
        return new User(name, surname, email, phone, passport);
    }
}
