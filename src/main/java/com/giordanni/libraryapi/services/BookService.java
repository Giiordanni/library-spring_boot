package com.giordanni.libraryapi.services;

import com.giordanni.libraryapi.dtos.books.RegistrationBookDTO;
import com.giordanni.libraryapi.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final IBookRepository bookRepository;

    public RegistrationBookDTO createBook(RegistrationBookDTO dto){
        // Lógica para criar um livro
        return dto;
    }
}
