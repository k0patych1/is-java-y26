package ru.itmo;

import ru.itmo.dao.IKittyDao;
import ru.itmo.dao.IOwnerDao;
import ru.itmo.dto.KittyDto;
import ru.itmo.dto.OwnerDto;
import ru.itmo.entity.Kitty;
import ru.itmo.entity.Owner;
import ru.itmo.mappers.KittyMapper;
import ru.itmo.mappers.OwnerMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class OwnerService implements IOwnerService {
    private final IOwnerDao ownerDao;

    private final IKittyDao kittenDao;

    public OwnerService(IOwnerDao ownerDao, IKittyDao kittenDao) {
        this.ownerDao = ownerDao;
        this.kittenDao = kittenDao;
    }

    @Override
    public Long createOwner(String name, LocalDate birthDate) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthDate(birthDate);
        owner.setKitties(Collections.emptyList());
        ownerDao.add(owner);

        return owner.getId();
    }

    @Override
    public void updateOwner(OwnerDto ownerDto) {
        Owner owner = ownerDao.getById(ownerDto.id());
        owner.setName(ownerDto.name());
        owner.setBirthDate(ownerDto.birthDate());
        ownerDao.update(owner);
    }

    @Override
    public void addKittyToOwner(Long kittyId, Long ownerId) {
        Owner owner = ownerDao.getById(ownerId);
        Kitty kitty = kittenDao.getById(kittyId);

        owner.getKitties().add(kitty);
        ownerDao.update(owner);
        kitty.setOwner(owner);
        kittenDao.update(kitty);
    }

    @Override
    public boolean removeOwner(Long id) {
        return ownerDao.removeById(id);
    }

    @Override
    public OwnerDto getOwnerById(Long id) {
        return OwnerMapper.asDto(ownerDao.getById(id));
    }

    @Override
    public List<KittyDto> findAllKittiesOfOwner(Long id) {
        return ownerDao.getAllKitties(id).stream()
                .map(KittyMapper::asDto)
                .toList();
    }

    @Override
    public List<OwnerDto> findAllOwners() {
        return ownerDao.getAll().stream()
                .map(OwnerMapper::asDto)
                .toList();
    }
}
