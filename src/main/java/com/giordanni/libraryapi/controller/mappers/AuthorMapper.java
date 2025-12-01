package com.giordanni.libraryapi.controller.mappers;

import com.giordanni.libraryapi.dtos.author.AuthorDTOs;
import com.giordanni.libraryapi.dtos.author.AuthorResponseDTOs;
import com.giordanni.libraryapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    // @Mapping(source = "nome no dto", target = "nome na entidade") // Exemplo de mapeamento customizado, se necessário
    Author toEntity(AuthorDTOs dto); // transforma dto em entidade

    AuthorResponseDTOs toDto(Author author); // transforma entidade em dto
}
