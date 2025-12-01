package com.giordanni.libraryapi.repository;

import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.model.GenderBooks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    IAuthorRepository authorRepository;

    @Autowired
    IBookRepository bookRepository;

    @Test
    public void saveAuthorTest() {
        Author author = new Author();
        author.setName("John Doe");
        author.setBirthDate(LocalDate.of(1970, 1, 1));
        author.setNationality("American");

        var authorSaved = authorRepository.save(author);
        System.out.println(authorSaved);
    }

    @Test
    public void updateAuthorTest(){
        var id = UUID.fromString("d16c3de6-c5d1-4223-a9cc-5b1437703c8b");

        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            Author authorToUpdate = author.get();
            authorToUpdate.setNationality("American");
            var authorUpdated = authorRepository.save(authorToUpdate);
            System.out.println(authorUpdated);
        } else {
            System.out.println("Author not found");
        }
    }

    @Test
    public void findAllAuthorsTest(){
        List<Author> authors = authorRepository.findAll();
        authors.forEach(System.out::println);
    }

    @Test
    public void CountAuthorsTest(){
        long count = authorRepository.count();
        System.out.println("Total authors: " + count);
    }

    @Test
    public void deleteAuthorByIdTest(){
        var id = UUID.fromString("d16c3de6-c5d1-4223-a9cc-5b1437703c8b");
        Optional<Author> author = authorRepository.findById(id);

        if(author.isPresent()) {
            authorRepository.deleteById(id);
            System.out.println("Author deleted with id: " + id);
        }else {
            System.out.println("Author not found with id: " + id);
        }
    }

    @Test
    public void deleteByObjectAuthorsTest(){
        var id = UUID.fromString("d16c3de6-c5d1-4223-a9cc-5b1437703c8b");
        Optional<Author> author = authorRepository.findById(id);

        if(author.isPresent()) {
            authorRepository.delete(author.get());
            System.out.println("Author deleted: " + author.get());
        } else {
            System.out.println("Author not found with id: " + id);
        }
    }

    @Test
    void savedAuthorWithBookTest(){
        Author author = new Author();
        author.setName("John Doe");
        author.setBirthDate(LocalDate.of(1970, 1, 1));
        author.setNationality("American");

        Book book = new Book();
        book.setTitle("The Great Gatsby");
        book.setIsbn("9780743273565");
        book.setGender(GenderBooks.FICTION);
        book.setPrice(BigDecimal.valueOf(23.89));
        book.setPublicationDate(LocalDate.of(2025, 5, 10));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setTitle("Castelo interior");
        book2.setIsbn("54823163598");
        book2.setGender(GenderBooks.BIOGRAPHY);
        book2.setPrice(BigDecimal.valueOf(23.89));
        book2.setPublicationDate(LocalDate.of(1891, 5, 10));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        authorRepository.save(author);
        bookRepository.saveAll(author.getBooks());
    }
}
