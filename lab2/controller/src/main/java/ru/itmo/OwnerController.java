package ru.itmo;

import ru.itmo.dto.KittyDto;
import ru.itmo.dto.OwnerDto;

import java.time.LocalDate;
import java.util.List;

public class OwnerController implements IOwnerController {
    private final IOwnerService ownerService;

    public OwnerController(IOwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @Override
    public Long createOwner(String name, LocalDate birthDate) {
        return ownerService.createOwner(name, birthDate);
    }

    @Override
    public void updateOwner(OwnerDto ownerDto) {
        ownerService.updateOwner(ownerDto);
    }

    @Override
    public void addKittyToOwner(Long kittyId, Long ownerId) {
        ownerService.addKittyToOwner(kittyId, ownerId);
    }

    @Override
    public boolean removeOwner(Long id) {
        return ownerService.removeOwner(id);
    }

    @Override
    public OwnerDto getOwnerById(Long id) {
        return ownerService.getOwnerById(id);
    }

    @Override
    public List<KittyDto> findAllKittiesOfOwner(Long id) {
        return ownerService.findAllKittiesOfOwner(id);
    }

    @Override
    public List<OwnerDto> findAllOwners() {
        return ownerService.findAllOwners();
    }
}
