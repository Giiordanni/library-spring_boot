package com.giordanni.libraryapi.services;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.giordanni.libraryapi.exceptions.OperationNorPermittedException;
import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.repository.IAuthorRepository;
import com.giordanni.libraryapi.repository.IBookRepository;
import com.giordanni.libraryapi.validators.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // criar um contrutor com os atributos finais - obrigatórios
public class AuthorServices {

    private final IAuthorRepository authorRepository;
    private final IBookRepository bookRepository;
    public final AuthorValidator authorValidator;

    public Author createAuthor(Author author) {
        authorValidator.validate(author);

        return authorRepository.save(author);
    }

    public Optional<Author> getByIdAuthor(UUID authorId) {
        return authorRepository.findById(authorId);
    }
    public void deleteAuthor(Author author) {
        if(existsBook(author)){
            throw new OperationNorPermittedException("Cannot delete author with associated books.");
        }
        authorRepository.delete(author);
    }

    public List<Author> searchAuthorsByNameAndNationality(String name, String nationality){
        if(name != null && nationality != null){
            return authorRepository.findByNameAndNationality(name, nationality);
        }
        else if(name != null){
            return authorRepository.findByName(name);
        }
        else if(nationality != null) {
            return authorRepository.findByNationality(nationality);
        }
        return authorRepository.findAll();
    }

    public Author updateAuthor(Author author) {
        authorValidator.validate(author);

        if(author.getId() == null){
            throw new IllegalArgumentException("Author ID cannot be null for update operation.");
        }
        return authorRepository.save(author);
    }

    private boolean existsBook(Author author){
        return bookRepository.existsByIdAuthor(author);
    }
}
