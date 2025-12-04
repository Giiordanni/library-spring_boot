package com.giordanni.libraryapi.controller;

import com.giordanni.libraryapi.controller.mappers.BookMapper;
import com.giordanni.libraryapi.dtos.books.RegistrationBookDTO;
import com.giordanni.libraryapi.dtos.books.ResultSearchBookDTO;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.model.GenderBooks;
import com.giordanni.libraryapi.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public ResponseEntity<ResultSearchBookDTO> getDetails(@PathVariable("id") String id) {
        return service.getBookById(UUID.fromString(id))
                .map(book -> {
                    var dto = mapper.toDto(book);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable("id") String id) {
        return service.getBookById(UUID.fromString(id))
                .map(book -> {
                    service.deleteBook(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    public ResponseEntity<Page<ResultSearchBookDTO>> searchBooksFilter(@RequestParam(value = "isbn", required = false) String isbn,
                                                                       @RequestParam(value = "title", required = false) String title,
                                                                       @RequestParam(value = "name-author", required = false) String nameAuthor,
                                                                       @RequestParam(value = "gender", required = false) GenderBooks gender,
                                                                       @RequestParam(value = "publication-year", required = false) Integer publicationYear,
                                                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                       @RequestParam(value = "size-page", defaultValue = "10") Integer size) {

        Page<Book> pageResult = service.searchBooksByFilter(isbn, title, nameAuthor, gender, publicationYear, page, size);

        Page<ResultSearchBookDTO> result = pageResult.map(mapper::toDto);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable("id") String id, @RequestBody RegistrationBookDTO dto) {
       return  service.getBookById(UUID.fromString(id))
                .map(book -> {
                    Book entityaux = mapper.toEntity(dto);
                    book.setPublicationDate(entityaux.getPublicationDate());
                    book.setGender(entityaux.getGender());
                    book.setIsbn(entityaux.getIsbn());
                    book.setPrice(entityaux.getPrice());
                    book.setTitle(entityaux.getTitle());
                    book.setAuthor(entityaux.getAuthor());

                    service.updateBook(book);

                    return ResponseEntity.noContent().build();

                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
