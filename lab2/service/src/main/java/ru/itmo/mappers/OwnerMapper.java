package ru.itmo.mappers;


import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import ru.itmo.dto.KittyDto;
import ru.itmo.dto.OwnerDto;
import ru.itmo.entity.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
@ExtensionMethod(KittyMapper.class)
public class OwnerMapper {
    public OwnerDto asDto(Owner owner) {
        List<Long> kittiesDto = new ArrayList<>();
        owner.getKitties().forEach(kitty -> kittiesDto.add(kitty.getId()));
        return new OwnerDto(owner.getId(), owner.getName(), owner.getBirthDate(), kittiesDto);
    }
}
