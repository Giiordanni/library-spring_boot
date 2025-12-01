package com.giordanni.libraryapi.repository;

import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.model.GenderBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IBookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthor(Author author);
    List<Book> findByTitle(String title);

    // named parameters
    @Query("""
            SELECT b FROM Book as b 
            WHERE b.gender = :genderName
            """)
    List<Book> findByGender(@Param("genderName") GenderBooks gender);

    // positional parameters
    @Query("""
            SELECT b FROM Book as b 
            WHERE b.gender = ?2 ORDER BY ?1
            """)
    List<Book> findByGenderPositional(@Param("order") String order, @Param("genderName") GenderBooks gender);

    boolean existsByAuthor(Author author);
}
