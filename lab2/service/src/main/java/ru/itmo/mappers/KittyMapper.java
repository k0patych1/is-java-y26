package ru.itmo.mappers;

import lombok.experimental.UtilityClass;
import ru.itmo.dto.KittyDto;
import ru.itmo.entity.Kitty;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class KittyMapper {
    public KittyDto asDto(Kitty kitty) {
        List<Long> friendsId = new ArrayList<>();
        kitty.getFriends().forEach(k -> friendsId.add(k.getId()));
        return new KittyDto(kitty.getId(), kitty.getName(), kitty.getBirthDate(), kitty.getBreed(),
                kitty.getColour(), kitty.getOwner().getId(), friendsId);
    }
}
