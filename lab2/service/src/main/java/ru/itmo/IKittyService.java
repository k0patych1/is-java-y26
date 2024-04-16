package ru.itmo;

import ru.itmo.dto.KittyDto;
import ru.itmo.dto.OwnerDto;
import ru.itmo.models.Breed;
import ru.itmo.models.Colour;

import java.time.LocalDate;
import java.util.List;

public interface IKittyService {
    Long addKitty(String name, LocalDate birthDate, Breed breed, Colour colour);

    OwnerDto getOwnerOfKitty(Long kittyId);

    boolean removeKitty(Long id);

    KittyDto getKittyById(Long id);

    KittyDto getKittyByName(String name);

    List<KittyDto> findAllFriendsOfKitty(Long id);

    List<KittyDto> findAllKitties();

    List<KittyDto> findKittiesByBreed(Breed breed);

    List<KittyDto> findKittiesByColour(Colour colour);

    void makeFriends(Long kittyId1, Long kittyId2);

    void unfriendKitties(Long kittyId1, Long kittyId2);
}
