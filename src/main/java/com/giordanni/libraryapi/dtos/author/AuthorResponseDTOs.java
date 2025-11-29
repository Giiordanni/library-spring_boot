package com.giordanni.libraryapi.dtos.author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorResponseDTOs(
        UUID id,
        String name,
        String nationality,
        LocalDate birthDate
) {

}
