package ru.itmo.dto;

import ru.itmo.models.Breed;
import ru.itmo.models.Colour;

import java.time.LocalDate;
import java.util.List;

public record KittyDto(Long id,
                       String name,
                       LocalDate birthDay,
                       Breed breed,
                       Colour colour,
                       Long ownerId,
                       List<Long> friendsId) {
}
