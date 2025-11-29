package com.giordanni.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@EntityListeners(AuditingEntityListener.class) // Habilita o listener de auditoria para preencher automaticamente os campos createdAt e updatedAt
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String isbn;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private LocalDate publicationDate;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private GenderBooks gender;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne //(cascade = CascadeType.ALL) // Indica que muitos livros podem ser escritos por um autor. Primeiro a entidade "muitos" (Book) referencia a entidade "um" (Author).
    @JoinColumn(name = "id_author")
    private Author idAuthor;

}
