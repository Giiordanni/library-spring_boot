package com.giordanni.libraryapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library API",
                version = "1.0",
                description = "API for managing a library system",
                contact = @Contact(
                        name = "Giordanni Alves Formiga",
                        email = "giordanniformiga103@gmail.com",
                        url = "https://github.com/Giiordanni"
                )
        )
)
public class OpenApiConfiguration {
}
