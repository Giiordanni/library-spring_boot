package com.giordanni.libraryapi.controller;

import com.giordanni.libraryapi.dtos.books.RegistrationBookDTO;
import com.giordanni.libraryapi.dtos.errors.ResponseError;
import com.giordanni.libraryapi.exceptions.RegisterDuplicateException;
import com.giordanni.libraryapi.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody @Valid RegistrationBookDTO dto){

        try{

            return ResponseEntity.ok(dto);
        } catch (RegisterDuplicateException e){
            var erroDto = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
