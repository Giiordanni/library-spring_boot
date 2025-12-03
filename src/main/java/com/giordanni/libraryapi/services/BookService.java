package com.giordanni.libraryapi.services;

import com.giordanni.libraryapi.dtos.books.RegistrationBookDTO;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.model.GenderBooks;
import com.giordanni.libraryapi.repository.IBookRepository;
import com.giordanni.libraryapi.repository.specs.BookSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final IBookRepository bookRepository;

    public Book createBook(Book dto){
        return bookRepository.save(dto);
    }

    public Optional<Book> getBookById(UUID id){
        return bookRepository.findById(id);
    }

    public void deleteBook(Book book){
        bookRepository.delete(book);
    }

    public List<Book> searchBooksByFilter(String isbn, String title, String nameAuthor, GenderBooks gender, Integer publicationYear){
//        Specification<Book> spec = Specification
//                .where(BookSpecs.isbnEqual(isbn))
//                .and(BookSpecs.titleLike(title))
//                .and((BookSpecs.genderEqual(gender)));

        // select * from book where 0 = 0;
        Specification<Book> specs = Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));

        if(isbn != null){
            specs = specs.and(BookSpecs.isbnEqual(isbn));
        }
        if(title != null){
            specs = specs.and(BookSpecs.titleLike(title));
        }
        if(gender != null){
            specs = specs.and(BookSpecs.genderEqual(gender));
        }
        if(publicationYear != null){
            specs = specs.and(BookSpecs.publicationYearEqual(publicationYear));
        }
        if(nameAuthor != null){
            specs = specs.and(BookSpecs.nameAuthorLike(nameAuthor));
        }

        return bookRepository.findAll(specs);

    }
}
