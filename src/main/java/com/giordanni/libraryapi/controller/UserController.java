package com.giordanni.libraryapi.controller;

import com.giordanni.libraryapi.controller.mappers.UserMapper;
import com.giordanni.libraryapi.dtos.user.UserDto;
import com.giordanni.libraryapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto dto){
        var user = mapper.toEntity(dto);
        userService.saveUser(user);
    }
}
