package ru.itmo.dto;

import java.time.LocalDate;
import java.util.List;

public record OwnerDto(Long id, String name, LocalDate birthDate, List<Long> kittiesId) {

}
