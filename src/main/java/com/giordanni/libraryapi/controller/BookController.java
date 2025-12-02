package com.giordanni.libraryapi.controller;

import com.giordanni.libraryapi.controller.mappers.BookMapper;
import com.giordanni.libraryapi.dtos.books.RegistrationBookDTO;
import com.giordanni.libraryapi.dtos.errors.ResponseError;
import com.giordanni.libraryapi.exceptions.RegisterDuplicateException;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody @Valid RegistrationBookDTO dto) {
        Book bookToCreate = mapper.toEntity(dto);
        service.createBook(bookToCreate);
        var url = generateHeaderLocation(bookToCreate.getId());
        return ResponseEntity.created(url).build();
    }
}
