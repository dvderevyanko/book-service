package com.book.application.service;

import com.book.application.port.output.AuthorRepositoryOPort;
import com.book.domain.exception.DuplicateException;
import com.book.domain.exception.InvalidDataException;
import com.book.domain.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAuthorUseCaseTest {

    @InjectMocks
    private CreateAuthorUseCase target;
    @Mock
    private AuthorRepositoryOPort repository;

    @Test
    void Given_ValidAuthor_When_Handle_Then_AuthorSaved() {
        Author author = new Author();
        author.setName("Writer Writer");
        when(repository.saveAuthor(eq(author))).thenReturn(author);
        when(repository.getAuthorByName(eq(author.getName()))).thenReturn(Optional.empty());
        var result = target.handle(author);
        assertThat(result).isSameAs(author);
        verify(repository).saveAuthor(eq(author));
        verify(repository).getAuthorByName(eq(author.getName()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void Given_NotValidAuthor_When_Handle_Then_ErrorThrown(String authorName) {
        Author author = new Author();
        author.setName(authorName);
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> target.handle(author));
        verify(repository, never()).saveAuthor(any());
    }

    @Test
    void Given_DuplicatedAuthor_When_Handle_Then_ErrorThrown() {
        Author author = new Author();
        author.setName("Writer Writer");
        when(repository.getAuthorByName(eq(author.getName()))).thenReturn(Optional.of(new Author()));
        assertThatExceptionOfType(DuplicateException.class).isThrownBy(() -> target.handle(author));
        verify(repository, never()).saveAuthor(any());
        verify(repository).getAuthorByName(eq(author.getName()));
    }

}