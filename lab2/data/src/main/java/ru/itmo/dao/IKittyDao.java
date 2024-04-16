package ru.itmo.dao;

import ru.itmo.entity.Kitty;
import ru.itmo.entity.Owner;
import ru.itmo.models.Breed;
import ru.itmo.models.Colour;

import java.util.List;

public interface IKittyDao {
    void add(Kitty kitty);

    void update(Kitty kitty);

    boolean removeById(Long id);

    Owner getOwner(Long kittyId);

    Kitty getById(Long id);

    Kitty getByName(String name);

    List<Kitty> getAll();

    List<Kitty> getAllFriends(Long id);

    List<Kitty> findAllWithBreed(Breed breed);

    List<Kitty> findAllWithColor(Colour colour);
}
