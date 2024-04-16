import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.itmo.IKittyService;
import ru.itmo.IOwnerService;
import ru.itmo.KittyService;
import ru.itmo.OwnerService;
import ru.itmo.dao.IKittyDao;
import ru.itmo.dao.IOwnerDao;
import ru.itmo.dao.KittyDao;
import ru.itmo.dao.OwnerDao;
import ru.itmo.dto.KittyDto;
import ru.itmo.dto.OwnerDto;
import ru.itmo.entity.Kitty;
import ru.itmo.entity.Owner;
import ru.itmo.models.Breed;
import ru.itmo.models.Colour;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceTest {
    @Test
    void addKittyToOwnerTest() {
        IKittyDao kittyDao = Mockito.mock(KittyDao.class);
        IOwnerDao ownerDao = Mockito.mock(OwnerDao.class);

        IKittyService kittyService = new KittyService(kittyDao);
        IOwnerService ownerService = new OwnerService(ownerDao, kittyDao);

        Long ownerId = 1L;
        Long kittyId = 2L;

        Kitty kitty = new Kitty();
        kitty.setId(kittyId);
        kitty.setName("Emos");
        kitty.setColour(Colour.Black);
        kitty.setBirthDate(LocalDate.now());
        kitty.setFriends(Collections.emptyList());
        kitty.setBreed(Breed.Unknown);

        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setBirthDate(LocalDate.now().minusDays(1));
        owner.setName("ruslan");
        owner.setKitties(new ArrayList<>());

        Mockito.when(kittyDao.getById(kittyId)).thenReturn(kitty);
        Mockito.when(ownerDao.getById(ownerId)).thenReturn(owner);

        ownerService.addKittyToOwner(kittyId, ownerId);

        Assertions.assertEquals(ownerService.getOwnerById(ownerId).id(),
                kittyService.getKittyById(kittyId).ownerId());
    }

    @Test
    public void kittyMappingTest() {
        IOwnerDao ownerDao = Mockito.mock(OwnerDao.class);
        IKittyDao kittyDao = Mockito.mock(KittyDao.class);

        IOwnerService ownerService = new OwnerService(ownerDao, kittyDao);

        Long ownerId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setName("Ruslan");
        owner.setBirthDate(LocalDate.now());

        Kitty kitty1 = new Kitty();
        kitty1.setId(1L);
        kitty1.setName("persik");
        kitty1.setColour(Colour.Calico);
        kitty1.setFriends(Collections.emptyList());
        kitty1.setBirthDate(LocalDate.MIN);
        kitty1.setOwner(owner);
        kitty1.setBreed(Breed.Bengal);

        Kitty kitty2 = new Kitty();
        kitty2.setId(2L);
        kitty2.setName("Begemot");
        kitty2.setFriends(Collections.emptyList());
        kitty2.setColour(Colour.Fawn);
        kitty2.setOwner(owner);
        kitty2.setBirthDate(LocalDate.now());
        kitty2.setBreed(Breed.Munchkin);

        List<Kitty> kitties = new ArrayList<>();
        kitties.add(kitty1);
        kitties.add(kitty2);


        Mockito.when(ownerDao.getAllKitties(ownerId))
                .thenReturn(kitties);

        KittyDto kittyDto1 = new KittyDto(1L, "persik", LocalDate.MIN, Breed.Bengal, Colour.Calico, ownerId, Collections.emptyList());
        KittyDto kittyDto2 = new KittyDto(2L, "Begemot", LocalDate.now(), Breed.Munchkin, Colour.Fawn, ownerId, Collections.emptyList());

        Assertions.assertEquals(ownerService.findAllKittiesOfOwner(ownerId), List.of(kittyDto1, kittyDto2));
    }

    @Test
    public void ownerMappingTest() {
        IKittyDao kittyDao = Mockito.mock(KittyDao.class);

        IKittyService kittyService = new KittyService(kittyDao);

        Long ownerId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setName("Ruslan");
        owner.setBirthDate(LocalDate.now());

        Kitty kitty1 = new Kitty();
        kitty1.setId(1L);
        kitty1.setName("persik");
        kitty1.setColour(Colour.Calico);
        kitty1.setFriends(Collections.emptyList());
        kitty1.setBirthDate(LocalDate.MIN);
        kitty1.setOwner(owner);
        kitty1.setBreed(Breed.Bengal);

        owner.setKitties(List.of(kitty1));

        Mockito.when(kittyDao.getOwner(1L)).thenReturn(owner);

        OwnerDto ownerDto = new OwnerDto(1L, "Ruslan", LocalDate.now(), List.of(1L));

        Assertions.assertEquals(kittyService.getOwnerOfKitty(kitty1.getId()), ownerDto);
    }
}
