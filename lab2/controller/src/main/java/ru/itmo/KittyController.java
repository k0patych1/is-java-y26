package ru.itmo;

import ru.itmo.dto.KittyDto;
import ru.itmo.dto.OwnerDto;
import ru.itmo.models.Breed;
import ru.itmo.models.Colour;

import java.time.LocalDate;
import java.util.List;

public class KittyController implements IKittyController {
    private final IKittyService kittyService;

    public KittyController(IKittyService kittyService) {
        this.kittyService = kittyService;
    }

    @Override
    public Long createKitty(String name, LocalDate birthDay, Breed breed, Colour colour) {
        return kittyService.addKitty(name, birthDay, breed, colour);
    }

    @Override
    public boolean removeKitty(Long id) {
        return kittyService.removeKitty(id);
    }

    @Override
    public OwnerDto getOwnerOfKitty(Long kittyId) {
        return kittyService.getOwnerOfKitty(kittyId);
    }

    @Override
    public KittyDto getKittyById(Long id) {
        return kittyService.getKittyById(id);
    }

    @Override
    public KittyDto getKittyByName(String name) {
        return kittyService.getKittyByName(name);
    }

    @Override
    public List<KittyDto> findAllFriendsOfKitty(Long id) {
        return kittyService.findAllFriendsOfKitty(id);
    }

    @Override
    public List<KittyDto> findAllKitties() {
        return kittyService.findAllKitties();
    }

    @Override
    public List<KittyDto> findKittiesByBreed(Breed breed) {
        return kittyService.findKittiesByBreed(breed);
    }

    @Override
    public List<KittyDto> findKittiesByColour(Colour colour) {
        return kittyService.findKittiesByColour(colour);
    }

    @Override
    public void makeFriends(Long kittyId1, Long kittyId2) {
        kittyService.makeFriends(kittyId1, kittyId2);
    }

    @Override
    public void unfriendKitties(Long kittyId1, Long kittyId2) {
        kittyService.unfriendKitties(kittyId1, kittyId2);
    }
}
