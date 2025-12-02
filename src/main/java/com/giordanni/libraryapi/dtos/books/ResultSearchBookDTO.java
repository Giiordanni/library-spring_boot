package com.giordanni.libraryapi.dtos.books;

import com.giordanni.libraryapi.dtos.author.AuthorResponseDTOs;
import com.giordanni.libraryapi.model.GenderBooks;
import java.util.UUID;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResultSearchBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        GenderBooks gender,
        BigDecimal price,
        AuthorResponseDTOs author
) {
}
