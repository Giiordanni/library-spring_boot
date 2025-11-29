package com.giordanni.libraryapi.repository;

import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.model.GenderBooks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    IBookRepository bookRepository;

    @Autowired
    IAuthorRepository authorRepository;

    @Test
    void saveBookTest() {
        Book book = new Book();
        book.setTitle("The Great Gatsby");
        book.setIsbn("9780743273565");
        book.setGender(GenderBooks.FICTION);
        book.setPrice(BigDecimal.valueOf(23.89));
        book.setPublicationDate(LocalDate.of(2025, 5, 10));

        Author author = authorRepository
                .findById(UUID.fromString("8fe19d5d-bf46-4db8-9abd-550aebeaecf9"))
                .orElse(null);

        book.setIdAuthor(author);

        Book savedBook = bookRepository.save(book);
        System.out.println(savedBook);
    }

    @Test
    void saveCascadeBookTest() {
        Book book = new Book();
        book.setTitle("The Great Gatsby");
        book.setIsbn("9780743273565");
        book.setGender(GenderBooks.FICTION);
        book.setPrice(BigDecimal.valueOf(23.89));
        book.setPublicationDate(LocalDate.of(2025, 5, 10));

        Author author = new Author();
        author.setName("Giordanni");
        author.setBirthDate(LocalDate.of(20003, 10, 8));
        author.setNationality("American");

        book.setIdAuthor(author);
        Book savedBook = bookRepository.save(book);
        System.out.println(savedBook);
    }

    @Test
    void delteBookTest(){
        UUID id = UUID.fromString("c76deae1-1911-40e7-abb5-0c5505b8378f");
        bookRepository.deleteById(id);
    }

    @Test
    void delteCascadeBookTest(){
        UUID id = UUID.fromString("182b8890-5e22-4a0a-a384-68b644e1b192");
        bookRepository.deleteById(id);
    }

    @Test
    void findBookAndAuthorTest(){
        UUID id = UUID.fromString("3852138f-b889-4640-bd17-6755cce9c977");
        Book book = bookRepository.findById(id).orElse(null);
        System.out.println("Author: " + book.getIdAuthor().getName());

    }

    @Test
    void findBookByTitle(){
        List<Book> books = bookRepository.findByTitle("Castelo interior");
        books.forEach(System.out::println);
    }

    @Test
    void findBookByGender(){
        List<Book> books = bookRepository.findByGender(GenderBooks.BIOGRAPHY);
        books.forEach(System.out::println);
    }

    @Test
    void findBookByGenderPositional(){
        List<Book> books = bookRepository.findByGenderPositional("price", GenderBooks.BIOGRAPHY);
        books.forEach(System.out::println);
    }
}