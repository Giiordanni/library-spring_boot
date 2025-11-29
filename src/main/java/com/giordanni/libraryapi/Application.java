package com.giordanni.libraryapi;

import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.repository.IAuthorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing // Habilita o suporte à auditoria JPA para preencher automaticamente os campos createdAt e updatedAt
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}
