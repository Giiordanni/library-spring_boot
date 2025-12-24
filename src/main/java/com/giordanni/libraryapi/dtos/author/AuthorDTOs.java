package com.giordanni.libraryapi.dtos.author;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "Author") // para o swagger descrever o DTO
public record AuthorDTOs (
       @NotBlank(message = "Name is required")
       @Size(max = 100, min = 02, message = "Name must be at most 100 characters and at least 2 characters")
       @Schema(name = "name") // para o swagger descrever o campo
       String name, // notblank é para dizer que as strings não podem ser nulas ou vazias
       @NotBlank(message = "Nationality is required")
       @Size(max = 50, min = 02, message = "Nationality must be at most 50 characters and at least 2 characters")
       String nationality,
       @NotNull(message = "BirthDate is required")
       @Past(message = "BirthDate must be a past date")
       LocalDate birthDate
) {
}
