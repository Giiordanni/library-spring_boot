package com.giordanni.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author") // schema "public" posso definir schemas no JPA, mas o padrão é "public"
@Data
@EntityListeners(AuditingEntityListener.class) // Habilita o listener de auditoria para preencher automaticamente os campos createdAt e updatedAt
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column( nullable = false, length = 50)
    private String nationality;

    @Column(nullable = false)
    private LocalDate birthDate;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "id_user")
    private UUID idUser;

    @OneToMany(mappedBy = "author") // mappedBy indica que a entidade Book possui a chave estrangeira (idAuthor) que referencia o Author. Não cria a coluna na tabela Author.
    private List<Book> books;

}
