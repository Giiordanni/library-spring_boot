package com.giordanni.libraryapi.repository.specs;

import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.model.GenderBooks;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isgn){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isbn"), isgn);
    }

    public static Specification<Book> titleLike(String title){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("title")),
                        "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genderEqual(GenderBooks gender){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("gender"), gender);
    }

    public static Specification<Book> publicationYearEqual(Integer publicationYear){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.function("to_char", String.class, root.get("publicationDate"), criteriaBuilder.literal("YYYY")),
                        publicationYear.toString()
                );
    }

    public static Specification<Book> nameAuthorLike(String nameAuthor){
        return (root, query, criteriaBuilder) -> {
//            return criteriaBuilder.like(criteriaBuilder.upper(root.get("author").get("name")), "%" + nameAuthor.toUpperCase() + "%");

            // utilizando join
            Join<Object, Object> joinAuthor = root.join("author", JoinType.LEFT);
                return criteriaBuilder.like(criteriaBuilder.upper(joinAuthor.get("name")), "%" + nameAuthor.toUpperCase() + "%");
            };
    }
}
