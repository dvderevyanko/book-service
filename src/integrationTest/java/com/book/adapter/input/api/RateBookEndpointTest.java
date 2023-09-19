package com.book.adapter.input.api;

import com.book.adapter.input.dto.BookRateDto;
import com.book.adapter.input.dto.BookResponseDto;
import com.book.adapter.output.entity.AuthorEntity;
import com.book.adapter.output.entity.BookEntity;
import com.book.domain.model.BookType;
import com.book.shared.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RateBookEndpointTest extends IntegrationTest {

    private static final String PATH = "/books";

    @BeforeEach
    public void init() {
        resetDb();
    }

    @Test
    void Given_Rate_When_Handle_Then_BooksWithNewRateReturned() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Writer");
        author = authorRepository.save(author);

        BookEntity book = new BookEntity();
        book.setAuthor(author);
        book.setType(BookType.HORROR);
        book.setTitle("title");
        book.setRateCount(1);
        book.setTotalRate(1);
        book.setAverageRate(1);

        Long bookId = bookRepository.save(book).getId();

        BookRateDto rate = new BookRateDto();
        rate.setRate(5);

        ResponseEntity<BookResponseDto> rateBookResponse = patch(PATH + "/" + bookId, rate, BookResponseDto.class);

        assertEquals(HttpStatus.OK, rateBookResponse.getStatusCode());
        assertThat(rateBookResponse.getBody())
                .hasNoNullFieldsOrProperties()
                .matches(r -> r.getTitle().equals(book.getTitle()))
                .matches(r -> r.getAverageRate().equals(3))
                .matches(r -> r.getType().equals(book.getType().getName()))
                .matches(r -> r.getAuthor().getId().equals(book.getAuthor().getId()))
                .matches(r -> r.getAuthor().getName().equals(book.getAuthor().getName()));
    }

    @Test
    void Given_SetRateNotExistedBook_When_Handle_Then_ErrorReturned() {
        BookRateDto rate = new BookRateDto();
        rate.setRate(5);
        ResponseEntity<RestError> rateBookResponse = patch(PATH + "/1", rate, RestError.class);
        assertThat(rateBookResponse)
                .matches(r -> r.getStatusCode() == HttpStatus.NOT_FOUND)
                .matches(r -> r.getBody().getMessage().equals("Book not found"));
    }

    @Test
    void Given_SetInvalidRate_When_Handle_Then_ErrorReturned() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Writer");
        author = authorRepository.save(author);

        BookEntity book = new BookEntity();
        book.setAuthor(author);
        book.setType(BookType.HORROR);
        book.setTitle("title");
        book.setRateCount(1);
        book.setTotalRate(1);
        book.setAverageRate(1);

        Long bookId = bookRepository.save(book).getId();

        BookRateDto rate = new BookRateDto();
        rate.setRate(10);

        ResponseEntity<RestError> rateBookResponse = patch(PATH + "/" + bookId, rate, RestError.class);
        assertThat(rateBookResponse)
                .matches(r -> r.getStatusCode() == HttpStatus.BAD_REQUEST)
                .matches(r -> r.getBody().getMessage().equals("Invalid fields : rate"));
    }
}