package com.giordanni.libraryapi.services;

import com.giordanni.libraryapi.dtos.books.RegistrationBookDTO;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final IBookRepository bookRepository;

    public Book createBook(Book dto){
        return bookRepository.save(dto);
    }

    public Optional<Book> getBookById(UUID id){
        return bookRepository.findById(id);
    }

    public void deleteBook(Book book){
        bookRepository.delete(book);
    }


}
