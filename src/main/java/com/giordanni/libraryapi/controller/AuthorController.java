package com.giordanni.libraryapi.controller;

import com.giordanni.libraryapi.controller.mappers.AuthorMapper;
import com.giordanni.libraryapi.dtos.author.AuthorDTOs;
import com.giordanni.libraryapi.dtos.author.AuthorResponseDTOs;
import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.model.User;
import com.giordanni.libraryapi.services.AuthorServices;
import com.giordanni.libraryapi.services.UserService;
import com.giordanni.libraryapi.seucurity.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(name = "Authors") // para o swagger
public class AuthorController implements GenericController {

    private final AuthorServices authorServices;
    // private final UserService userService;
    private final AuthorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create", description = "Endpoint to create a new author") // para o swagger descrever o endpoint
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "402", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Author already exists")
    })
    public ResponseEntity<Object> createAuthor(@RequestBody @Valid AuthorDTOs dto) { //  Authentication auth

        // subistitui tudo dentro do authorservices
        // UserDetails userLogin = (UserDetails) auth.getPrincipal();
       // User user = userService.getByLogin(userLogin.getUsername());

        Author author = mapper.toEntity(dto);
        // author.setIdUser(user.getId());
        authorServices.createAuthor(author);

        URI location = generateHeaderLocation(author.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    @Operation(summary = "Get Author Details", description = "Endpoint to get author details by ID") // para o swagger descrever o endpoint
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public ResponseEntity<AuthorResponseDTOs> getAuthorDetails(@PathVariable("id") UUID authorId) {
        Optional<Author> authorOtional = authorServices.getByIdAuthor(authorId);

        return authorServices
                .getByIdAuthor(authorId)
                .map(author -> {
                    AuthorResponseDTOs authorResponse = mapper.toDto(author);
                    return ResponseEntity.ok(authorResponse);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    @Operation(summary = "Get Author with Filters", description = "Endpoint to get author with filters") // para o swagger descrever o endpoint
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Authors not found")
    })
    public ResponseEntity<List<AuthorResponseDTOs>> searchAuthorsUsingFilters(@RequestParam(value = "name", required = false) String name,
                                                                              @RequestParam(value = "nationality", required = false) String nationality) {

        List<Author> results = authorServices.searchAuthorsByExample(name, nationality);
        List<AuthorResponseDTOs> authorDto = results
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(authorDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Author", description = "Endpoint to delete an author by ID") // para o swagger descrever o endpoint
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "400", description = "Author cannot be deleted because it is associated with existing books")
    })
    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") UUID authorId) {

        Optional<Author> authorOtional = authorServices.getByIdAuthor(authorId);
        if (authorOtional.isEmpty()) return ResponseEntity.notFound().build();

        authorServices.deleteAuthor(authorOtional.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update Author", description = "Endpoint to update an existing author") // para o swagger descrever o endpoint
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "402", description = "Invalid request data")
    })
    public ResponseEntity<Object> updateAuthor(@PathVariable("id") UUID id, @RequestBody @Valid AuthorDTOs dtOs) {

        Optional<Author> authorOtional = authorServices.getByIdAuthor(id);
        if (authorOtional.isEmpty()) return ResponseEntity.notFound().build();

        var author = authorOtional.get();
        author.setName(dtOs.name());
        author.setNationality(dtOs.nationality());
        author.setBirthDate(dtOs.birthDate());

        authorServices.updateAuthor(author);
        return ResponseEntity.ok().build();

    }

}
