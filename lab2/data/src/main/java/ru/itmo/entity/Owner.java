package ru.itmo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@Entity
@Data
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private LocalDate birthDate;
    @OneToMany(mappedBy = "owner", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Kitty> kitties;

    public Owner(String name, LocalDate birthDate, List<Kitty> kitties) {
        this.name = name;
        this.birthDate = birthDate;
        this.kitties = kitties;
    }

    public void addKitty(Kitty kitty) {
        kitties.add(kitty);
    }

    public void removeKitty(Kitty kitty) {
        kitties.removeIf(k -> k.getId() == kitty.getId());
    }
}
