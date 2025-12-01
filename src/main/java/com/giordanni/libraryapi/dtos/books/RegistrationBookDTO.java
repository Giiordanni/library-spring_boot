package com.giordanni.libraryapi.dtos.books;

import com.giordanni.libraryapi.model.GenderBooks;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegistrationBookDTO(
        @NotBlank(message = "Isbn is required")
        @ISBN
        String isbn,

        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Publication Date is required")
        @Past(message = "Publication Date must be a past date")
        LocalDate publicationDate,

        GenderBooks gender,

        BigDecimal price,

        @NotNull(message = "Author ID is required")
        UUID idAuthor
) {
}
