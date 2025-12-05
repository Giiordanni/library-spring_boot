package com.giordanni.libraryapi.controller.mappers;

import com.giordanni.libraryapi.dtos.user.UserDto;
import com.giordanni.libraryapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User user);
}
