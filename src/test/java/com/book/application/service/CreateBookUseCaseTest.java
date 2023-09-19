package com.book.application.service;

import com.book.application.port.output.AuthorRepositoryOPort;
import com.book.application.port.output.BookRepositoryOPort;
import com.book.domain.exception.DuplicateException;
import com.book.domain.exception.InvalidDataException;
import com.book.domain.exception.ObjectNotFoundException;
import com.book.domain.model.Author;
import com.book.domain.model.Book;
import com.book.domain.model.BookType;
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
public class CreateBookUseCaseTest {

    @InjectMocks
    private CreateBookUseCase target;
    @Mock
    private BookRepositoryOPort bookRepository;
    @Mock
    private AuthorRepositoryOPort authorRepository;

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void Given_ValidBook_When_Handle_Then_BookSaved(Integer rate) {
        Book book = createBook("book", BookType.HORROR);
        when(bookRepository.saveBook(eq(book))).thenReturn(book);
        when(bookRepository.getBookByTitle(eq(book.getTitle()))).thenReturn(Optional.empty());
        when(authorRepository.getAuthorById(eq(book.getAuthor().getId()))).thenReturn(Optional.of(book.getAuthor()));
        var result = target.handle(book, rate);
        assertThat(result).isSameAs(book);
        verify(bookRepository).saveBook(eq(book));
        verify(bookRepository).getBookByTitle(eq(book.getTitle()));
    }

    @Test
    void Given_BookWithInvalidRate_When_Handle_Then_ErrorThrown() {
        Book book = createBook("book", BookType.HORROR);
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> target.handle(book, 10));
    }


    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void Given_BookWithInvalidTitle_When_Handle_Then_ErrorThrown(String title) {
        Book book = createBook(title, BookType.HORROR);
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> target.handle(book, null))
                .withMessage("Invalid fields : title");
        verify(bookRepository, never()).saveBook(eq(book));
    }

    @Test
    void Given_BookWithInvalidType_When_Handle_Then_ErrorThrown() {
        Book book = createBook("book", null);
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> target.handle(book, null))
                .withMessage("Invalid fields : type");
        verify(bookRepository, never()).saveBook(eq(book));
    }

    @Test
    void Given_BookWithSomeInvalidFields_When_Handle_Then_ErrorThrown() {
        Book book = createBook("", null);
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> target.handle(book, 10))
                .withMessageContaining("Invalid fields :")
                .withMessageContaining("type")
                .withMessageContaining("title")
                .withMessageContaining("rate");
        verify(bookRepository, never()).saveBook(eq(book));
    }

    @Test
    void Given_DuplicatedBookTitle_When_Handle_Then_ErrorThrown() {
        Book book = createBook("book", BookType.HORROR);
        when(bookRepository.getBookByTitle(eq(book.getTitle()))).thenReturn(Optional.of(new Book()));
        assertThatExceptionOfType(DuplicateException.class).isThrownBy(() -> target.handle(book, null))
                .withMessage("Object already exists");
    }

    @Test
    void Given_AuthorNotExists_When_Handle_Then_ErrorThrown() {
        Book book = createBook("book", BookType.HORROR);
        when(bookRepository.getBookByTitle(eq(book.getTitle()))).thenReturn(Optional.empty());
        when(authorRepository.getAuthorById(eq(book.getAuthor().getId()))).thenReturn(Optional.empty());
        assertThatExceptionOfType(ObjectNotFoundException.class).isThrownBy(() -> target.handle(book, null))
                .withMessage("Object author not found");
        verify(bookRepository).getBookByTitle(eq(book.getTitle()));
    }

    private Book createBook(String title, BookType type) {
        Author author = new Author();
        author.setId(1L);
        author.setName("Writer Writer");
        Book book = new Book();
        book.setTitle(title);
        book.setType(type);
        book.setAuthor(author);
        return book;
    }

}