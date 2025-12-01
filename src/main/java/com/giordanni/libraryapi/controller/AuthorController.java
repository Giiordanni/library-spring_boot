package com.giordanni.libraryapi.controller;

import com.giordanni.libraryapi.controller.mappers.AuthorMapper;
import com.giordanni.libraryapi.dtos.author.AuthorDTOs;
import com.giordanni.libraryapi.dtos.author.AuthorResponseDTOs;
import com.giordanni.libraryapi.dtos.errors.ResponseError;
import com.giordanni.libraryapi.exceptions.OperationNorPermittedException;
import com.giordanni.libraryapi.exceptions.RegisterDuplicateException;
import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.services.AuthorServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorServices authorServices;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<Object> createAuthor(@RequestBody @Valid AuthorDTOs dto) {
        try {
            Author author = mapper.toEntity(dto);
            authorServices.createAuthor(author);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(author.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegisterDuplicateException e) {
            var errorDto = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDto.status()).body(errorDto);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTOs> getAuthorDetails(@PathVariable("id") UUID authorId){
        Optional<Author> authorOtional = authorServices.getByIdAuthor(authorId);

        return authorServices
                .getByIdAuthor(authorId)
                .map(author -> {
                    AuthorResponseDTOs authorResponse = mapper.toDto(author);
                    return ResponseEntity.ok(authorResponse);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTOs>> searchAuthorsUsingFilters(@RequestParam(value = "name", required = false) String name,
                                                                              @RequestParam(value = "nationality", required = false) String nationality){

        List<Author> results = authorServices.searchAuthorsByExample(name, nationality);
        List<AuthorResponseDTOs> authorDto = results
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(authorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") UUID authorId){
        try {
            Optional<Author> authorOtional = authorServices.getByIdAuthor(authorId);
            if(authorOtional.isEmpty()) return ResponseEntity.notFound().build();

            authorServices.deleteAuthor(authorOtional.get());
            return ResponseEntity.noContent().build();

        }catch (OperationNorPermittedException e) {
            var errorResponse = ResponseError.standardResponse(e.getMessage());
            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable("id") UUID id, @RequestBody @Valid AuthorDTOs dtOs){
        try {
            Optional<Author> authorOtional = authorServices.getByIdAuthor(id);
            if(authorOtional.isEmpty()) return ResponseEntity.notFound().build();

            var author = authorOtional.get();
            author.setName(dtOs.name());
            author.setNationality(dtOs.nationality());
            author.setBirthDate(dtOs.birthDate());

            authorServices.updateAuthor(author);
            return ResponseEntity.ok().build();

        } catch (RegisterDuplicateException e) {
            var errorDto = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDto.status()).body(errorDto);
        }
    }

}
