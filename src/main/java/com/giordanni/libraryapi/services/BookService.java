package com.giordanni.libraryapi.services;

import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.model.GenderBooks;
import com.giordanni.libraryapi.model.User;
import com.giordanni.libraryapi.repository.IBookRepository;
import com.giordanni.libraryapi.repository.specs.BookSpecs;
import com.giordanni.libraryapi.seucurity.SecurityService;
import com.giordanni.libraryapi.validators.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final IBookRepository bookRepository;
    private final BookValidator bookValidator;
    private final SecurityService securityService;

    public Book createBook(Book book){
        bookValidator.validate(book);

        User user = securityService.getUserLogin();
        book.setUser(user);

        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(UUID id){
        return bookRepository.findById(id);
    }

    public void deleteBook(Book book){
        bookRepository.delete(book);
    }

    public Page<Book> searchBooksByFilter(
            String isbn,
            String title,
            String nameAuthor,
            GenderBooks gender,
            Integer publicationYear,
            Integer page, Integer sizePage){


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

        Pageable pageRequest = PageRequest.of(page, sizePage);

        return bookRepository.findAll(specs, pageRequest);

    }
    public void updateBook(Book book){
        if(book.getId() == null){
            throw new IllegalArgumentException("Book ID cannot be null for update operation.");
        }

        bookValidator.validate(book);
        bookRepository.save(book);
    }
}
