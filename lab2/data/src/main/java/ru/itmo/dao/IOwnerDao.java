package ru.itmo.dao;

import ru.itmo.entity.Kitty;
import ru.itmo.entity.Owner;

import java.util.List;

public interface IOwnerDao {
    void add(Owner owner);

    void update(Owner owner);

    void addKittyToOwner(Long kittyId, Long ownerId);

    boolean removeById(Long id);

    Owner getById(Long id);

    List<Owner> getAll();

    List<Kitty> getAllKitties(Long id);
}
