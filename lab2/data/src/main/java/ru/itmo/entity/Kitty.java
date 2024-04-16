package ru.itmo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.models.Breed;
import ru.itmo.models.Colour;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "kitties")
public class Kitty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private LocalDate birthDate;
    @Column
    private Breed breed;
    @Column
    private Colour colour;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Owner owner;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Kitty> friends;

    public Kitty(String name, LocalDate birthDate, Breed breed, Colour colour, Owner owner, List<Kitty> friends) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.colour = colour;
        this.owner = owner;
        this.friends = friends;
        friends.remove(this);
    }

    public void addFriend(Kitty friend) {
        friends.add(friend);
    }

    public void unfriend(Kitty friend) {
        friends.remove(friend);
    }
}
