package com.giordanni.libraryapi.services;

import com.giordanni.libraryapi.exceptions.OperationNotPermittedException;
import com.giordanni.libraryapi.exceptions.RegisterDuplicateException;
import com.giordanni.libraryapi.model.Author;
import com.giordanni.libraryapi.model.User;
import com.giordanni.libraryapi.repository.IAuthorRepository;
import com.giordanni.libraryapi.repository.IBookRepository;
import com.giordanni.libraryapi.seucurity.SecurityService;
import com.giordanni.libraryapi.validators.AuthorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServicesTest {

    @InjectMocks
    AuthorServices services;

    @Mock
    IAuthorRepository authorRepository;

    @Mock
    IBookRepository bookRepository;

    @Mock
    private AuthorValidator authorValidator;

    @Mock
    private SecurityService securityService;

    Author author;

    @BeforeEach
    void setUp(){
        author = new Author();
    }

    @Test
    void shouldThrowExceptionWhenAuthorAlreadyExistsOnCreate(){
        doThrow(new RegisterDuplicateException("There is already an author registered."))
                .when(authorValidator).validate(author);

        assertThrows(
                RegisterDuplicateException.class,
                () -> services.createAuthor(author)
        );

        verify(authorValidator).validate(author);
        verifyNoInteractions(authorRepository);
    }

    @Test
    void shouldBeCreateAuthorTest(){
        // Arrange (given)
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("giordanni@gmail.com");
        user.setLogin("giordanni");
        user.setRoles(List.of("ADMIN"));

        when(securityService.getUserLogin()).thenReturn(user);
        when(authorRepository.save(any(Author.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act (when)
        Author result = services.createAuthor(author);

        // Assert (then)
        assertNotNull(result);
        assertEquals(user, result.getUser());

        verify(authorValidator).validate(author);
        verify(securityService).getUserLogin();
        verify(authorRepository).save(author);
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void shouldGetAuthorByIdTest(){
        UUID authorId = UUID.randomUUID();
        author.setId(authorId);

        when(authorRepository.findById(authorId))
                .thenReturn(Optional.of(author));

        Optional<Author> result = services.getByIdAuthor(authorId);

        assertTrue(result.isPresent());
        assertEquals(author, result.get());

        verify(authorRepository).findById(authorId);
    }

    @Test
    void shouldDeleteAuthorWhenNoBooksExist(){
        // Arrange
        when(bookRepository.existsByAuthor(author)).thenReturn(false);

        // Act
        services.deleteAuthor(author);

        // Assert
        verify(authorRepository, Mockito.times(1)).delete(author);
    }

    @Test
    void shouldThrowExceptionWhenAuthorHasBooks(){
        // Arrange
        when(bookRepository.existsByAuthor(author)).thenReturn(true);

        // Act
        assertThrows(
                OperationNotPermittedException.class,
                () -> services.deleteAuthor(author)
        );

        verify(authorRepository, never()).delete(any());
    }

    @Test
    void shouldUpdateAuthorSuccessfully(){
        author.setId(UUID.randomUUID());
        author.setName("Giordanni");
        author.setNationality("US");

        var authorUpdated = new Author();
        authorUpdated.setId(author.getId());
        authorUpdated.setName("Giordanni Updated");
        authorUpdated.setNationality("BR");

        when(authorRepository.save(any())).thenReturn(authorUpdated);

        var result = services.updateAuthor(author);

        assertEquals("Giordanni Updated", result.getName());
        assertEquals("BR", result.getNationality());

        verify(authorValidator).validate(author);
        verify(authorRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdateAuthorWithNullId(){
        author.setName("Giordanni");
        author.setNationality("US");

        var error = assertThrows(
                IllegalArgumentException.class,
                () -> services.updateAuthor(author)
        );

        assertEquals("Author ID cannot be null for update operation.", error.getMessage());
        verify(authorValidator).validate(author);
        verify(authorRepository, never()).save(any());
    }

    @Test
    void shouldSearchAuthorsByNameAndNationalityTest() {
        author.setName("Giordanni");
        author.setNationality("BR");

        when(authorRepository.findByNameAndNationality(author.getName(), author.getNationality()))
                .thenReturn(List.of(author));

        var result = services.searchAuthorsByNameAndNationality(author.getName(), author.getNationality());

        assertEquals(1, result.size());
        assertEquals(author, result.get(0));
        assertEquals("Giordanni", result.getFirst().getName());

        verify(authorRepository, times(1)).findByNameAndNationality(author.getName(), author.getNationality());
        verify(authorRepository, never()).findByName(any());
        verify(authorRepository, never()).findByNationality(any());
    }

    @Test
    void shouldSearchAuthorByNameOnlyTest() {
        author.setName("Giordanni");

        when(authorRepository.findByName(author.getName()))
                .thenReturn(List.of(author));

        var result = services.searchAuthorsByNameAndNationality(author.getName(), null);

        assertEquals(1, result.size());
        assertEquals(author, result.get(0));
        assertEquals("Giordanni", result.getFirst().getName());

        verify(authorRepository, times(1)).findByName(author.getName());
        verify(authorRepository, never()).findByNameAndNationality(any(), any());
        verify(authorRepository, never()).findByNationality(any());
    }

    @Test
    void shouldSearchAuthorByNationalityOnlyTest() {
        author.setNationality("BR");

        when(authorRepository.findByNationality(author.getNationality()))
                .thenReturn(List.of(author));

        var result = services.searchAuthorsByNameAndNationality(null, author.getNationality());

        assertEquals(1, result.size());
        assertEquals(author, result.get(0));
        assertEquals("BR", result.getFirst().getNationality());

        verify(authorRepository, times(1)).findByNationality(author.getNationality());
        verify(authorRepository, never()).findByNameAndNationality(any(), any());
        verify(authorRepository, never()).findByName(any());
    }

    @Test
    void shouldFindAllAuthorsWhenNoParamsProvidedTest() {

        Author aut1 = new Author();
        Author aut2 = new Author();

        when(authorRepository.findAll())
                .thenReturn(List.of(aut1, aut2));

        List<Author> result = services.searchAuthorsByNameAndNationality(null, null);

        assertEquals(2, result.size());
        assertThat(result).hasSize(2);
        assertNotNull(result);

        verify(authorRepository, times(1)).findAll();
        verify(authorRepository, never()).findByNameAndNationality(any(), any());
        verify(authorRepository, never()).findByName(any());
        verify(authorRepository, never()).findByNationality(any());

    }

    @Test
    void shouldSearchAuthorsByExampleTest() {
         String name = "Giordanni";
         String nationality = "brasileiro";

         Author aut1 = new Author();
         aut1.setName("Giordanni Formiga");

         Author aut2 = new Author();
         aut2.setName("Giordanni Alves");

         when(authorRepository.findAll(any(Example.class))).thenReturn(List.of(aut1, aut2));

         // Act
        List<Author> result = services.searchAuthorsByExample(name, nationality);

        // Assert
        assertNotNull(result);
        assertThat(result).hasSize(2);

        verify(authorRepository, times(1)).findAll(any(Example.class));
    }


}