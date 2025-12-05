package com.giordanni.libraryapi.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDto(

        @NotBlank(message = "Login is required")
        String login,

        @NotBlank(message = "Login is required")
        String password,

        @NotNull(message = "At least one role is required")
        List<String> roles )



{
}
