package com.giordanni.libraryapi.validators;

import com.giordanni.libraryapi.exceptions.RegisterDuplicateException;
import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.repository.IAuthorRepository;
import com.giordanni.libraryapi.services.AuthorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorValidator {

    private final IAuthorRepository authorRepository;

    public void validate(Author author) {
        if(existsAuthorRegistered(author)){
            throw new RegisterDuplicateException("There is already an author registered.");
        }
    }

    private boolean existsAuthorRegistered(Author author) {
        Optional<Author> existingAuthor = authorRepository.findByNameAndBirthDateAndNationality(
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        );

        if(author.getId() == null){
            return existingAuthor.isPresent();
        } else {
            return existingAuthor.isPresent() && !existingAuthor.get().getId().equals(author.getId());
        }
    }
}
