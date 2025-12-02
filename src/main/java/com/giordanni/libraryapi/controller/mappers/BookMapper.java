package com.giordanni.libraryapi.controller.mappers;

import com.giordanni.libraryapi.dtos.books.RegistrationBookDTO;
import com.giordanni.libraryapi.dtos.books.ResultSearchBookDTO;
import com.giordanni.libraryapi.model.Book;
import com.giordanni.libraryapi.repository.IAuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    IAuthorRepository authorRepository;

    @Mapping(expression = "java( authorRepository.findById(dto.idAuthor()).orElse(null) )", target = "author")
    public abstract Book toEntity(RegistrationBookDTO dto);
    public abstract ResultSearchBookDTO toDto(Book book);
}
