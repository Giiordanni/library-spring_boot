package com.giordanni.libraryapi.services;

import com.giordanni.libraryapi.dtos.books.RegistrationBookDTO;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final IBookRepository bookRepository;

    public Book createBook(Book dto){
        return bookRepository.save(dto);
    }
}
