package com.giordanni.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, nullable = false, unique = true)
    private String login;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(length = 150, nullable = false)
    private String email;


    // no banco de dados esse campo roles será guardado como array de strings
    // o jpa não saberá traduzir uma lista de strings para um array no banco de dados
    // para resolver isso, precisamos usar uma biblioteca para isso, com hypersistence
    // para isso baixi a dependencia e coloquei o Type e o columnDefinition
    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;
}
