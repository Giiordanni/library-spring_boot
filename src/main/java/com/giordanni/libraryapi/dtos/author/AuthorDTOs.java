package com.giordanni.libraryapi.dtos.author;

import com.giordanni.libraryapi.model.Author;

import java.time.LocalDate;

public record AuthorDTOs (
        String name,
        String nationality,
        LocalDate birthDate
) {

    public Author mapperDtoToAuthor() {
         Author author = new Author();
            author.setName(this.name);
            author.setNationality(this.nationality);
            author.setBirthDate(this.birthDate);

         return author;
    }
}
