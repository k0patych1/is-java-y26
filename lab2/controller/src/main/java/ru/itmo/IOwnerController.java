package ru.itmo;

import ru.itmo.dto.KittyDto;
import ru.itmo.dto.OwnerDto;

import java.time.LocalDate;
import java.util.List;

public interface IOwnerController {
    Long createOwner(String name, LocalDate birthDate);

    void updateOwner(OwnerDto ownerDto);

    void addKittyToOwner(Long kittyId, Long ownerId);

    boolean removeOwner(Long id);

    OwnerDto getOwnerById(Long id);

    List<KittyDto> findAllKittiesOfOwner(Long id);

    List<OwnerDto> findAllOwners();
}
