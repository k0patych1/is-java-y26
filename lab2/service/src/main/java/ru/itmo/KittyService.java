package ru.itmo;

import ru.itmo.dao.IKittyDao;
import ru.itmo.dto.KittyDto;
import ru.itmo.dto.OwnerDto;
import ru.itmo.entity.Kitty;
import ru.itmo.mappers.KittyMapper;
import ru.itmo.mappers.OwnerMapper;
import ru.itmo.models.Breed;
import ru.itmo.models.Colour;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class KittyService implements IKittyService {
    private final IKittyDao kittyDao;

    public KittyService(IKittyDao kittyDao) {
        this.kittyDao = kittyDao;
    }

    @Override
    public Long addKitty(String name, LocalDate birthDate, Breed breed, Colour colour) {
        Kitty kitty = new Kitty();
        kitty.setName(name);
        kitty.setBirthDate(birthDate);
        kitty.setBreed(breed);
        kitty.setColour(colour);
        kitty.setFriends(Collections.emptyList());

        kittyDao.add(kitty);

        return kitty.getId();
    }

    @Override
    public OwnerDto getOwnerOfKitty(Long kittyId) {
        return OwnerMapper.asDto(kittyDao.getOwner(kittyId));
    }

    @Override
    public boolean removeKitty(Long id) {
        return kittyDao.removeById(id);
    }

    @Override
    public KittyDto getKittyById(Long id) {
        return KittyMapper.asDto(kittyDao.getById(id));
    }

    @Override
    public KittyDto getKittyByName(String name) {
        return KittyMapper.asDto(kittyDao.getByName(name));
    }

    @Override
    public List<KittyDto> findAllFriendsOfKitty(Long id) {
        return kittyDao.getAllFriends(id).stream()
                .map(KittyMapper::asDto)
                .toList();
    }

    @Override
    public List<KittyDto> findAllKitties() {
        return kittyDao.getAll().stream()
                .map(KittyMapper::asDto)
                .toList();
    }

    @Override
    public List<KittyDto> findKittiesByBreed(Breed breed) {
        return kittyDao.findAllWithBreed(breed).stream()
                .map(KittyMapper::asDto)
                .toList();
    }

    @Override
    public List<KittyDto> findKittiesByColour(Colour colour) {
        return kittyDao.findAllWithColor(colour).stream()
                .map(KittyMapper::asDto)
                .toList();
    }

    @Override
    public void makeFriends(Long kittyId1, Long kittyId2) {
        Kitty kitty1 = kittyDao.getById(kittyId1);
        Kitty kitty2 = kittyDao.getById(kittyId2);
        kitty1.addFriend(kitty2);
        kitty2.addFriend(kitty1);
        kittyDao.update(kitty1);
    }

    @Override
    public void unfriendKitties(Long kittyId1, Long kittyId2) {
        Kitty kitty1 = kittyDao.getById(kittyId1);
        Kitty kitty2 = kittyDao.getById(kittyId2);
        kitty1.unfriend(kitty2);
        kitty2.unfriend(kitty1);
        kittyDao.update(kitty1);
    }
}
