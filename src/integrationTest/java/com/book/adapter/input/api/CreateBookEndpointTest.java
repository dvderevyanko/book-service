package com.book.adapter.input.api;

import com.book.adapter.input.dto.AuthorDto;
import com.book.adapter.input.dto.BookResponseDto;
import com.book.adapter.input.dto.CreateBookDto;
import com.book.adapter.output.entity.AuthorEntity;
import com.book.shared.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateBookEndpointTest extends IntegrationTest {

    private static final String PATH = "/books";

    @BeforeEach
    public void init() {
        resetDb();
    }

    @Test
    void Given_CreateValidBook_When_Handle_Then_CreatedBookReturned() {
        final String authorName = "Writer";
        AuthorEntity author = new AuthorEntity();
        author.setName(authorName);
        author = authorRepository.save(author);

        CreateBookDto newBook = new CreateBookDto();
        newBook.setAuthorId(author.getId());
        newBook.setType("Horror");
        newBook.setTitle("title");

        ResponseEntity<BookResponseDto> createBookResponse = post(PATH, newBook, BookResponseDto.class);

        assertEquals(HttpStatus.OK, createBookResponse.getStatusCode());
        assertThat(createBookResponse.getBody())
                .hasNoNullFieldsOrProperties()
                .matches(r -> r.getTitle().equals(newBook.getTitle()))
                .matches(r -> r.getAverageRate().equals(0))
                .matches(r -> r.getType().equals(newBook.getType()))
                .matches(r -> r.getAuthor().getId().equals(newBook.getAuthorId()))
                .matches(r -> r.getAuthor().getName().equals(authorName));
        assertThat(bookRepository.findByTitle(newBook.getTitle())).isNotNull();
    }

    @Test
    void Given_CreateInvalidAuthor_When_Handle_Then_CreatedAuthorReturned() {
        ResponseEntity<AuthorDto> createAuthorResponse = post(PATH, new AuthorDto(), AuthorDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, createAuthorResponse.getStatusCode());
    }
}