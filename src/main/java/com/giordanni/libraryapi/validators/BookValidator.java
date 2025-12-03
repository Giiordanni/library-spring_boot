package com.giordanni.libraryapi.validators;

import com.giordanni.libraryapi.exceptions.RegisterDuplicateException;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final IBookRepository bookRepository;

    public void validate(Book book){
        if(existsBookWithIsbn(book)){
            throw new RegisterDuplicateException("There is already a book with this ISBN");
        }
    }

    private boolean existsBookWithIsbn(Book book){
        Optional<Book> bookExists = bookRepository.findByIsbn(book.getIsbn());

        if(book.getId() == null){
            return bookExists.isPresent();
        }

        return bookExists
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }
}
