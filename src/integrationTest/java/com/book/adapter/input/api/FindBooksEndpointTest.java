package com.book.adapter.input.api;

import com.book.adapter.input.dto.BookPageResponseDto;
import com.book.adapter.output.entity.AuthorEntity;
import com.book.adapter.output.entity.BookEntity;
import com.book.domain.model.BookType;
import com.book.shared.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindBooksEndpointTest extends IntegrationTest {

    private static final String PATH = "/books";

    @BeforeEach
    public void init() {
        resetDb();
    }

    @Test
    void Given_GetBooksByTitle_When_Handle_Then_MatchedBooksOrderedByRateReturned() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Writer");
        author = authorRepository.save(author);

        BookEntity book1 = initBook(author, "title", 1, 1);
        BookEntity book2 = initBook(author, "title1", 3, 3);
        BookEntity book3 = initBook(author, "title2", 5, 5);
        BookEntity book4 = initBook(author, "test", 1, 1);

        bookRepository.saveAll(List.of(book1, book2, book3, book4));

        ResponseEntity<BookPageResponseDto> findBooksResponse = get(PATH + "?page=0&size=10&title=title", BookPageResponseDto.class);

        assertEquals(HttpStatus.OK, findBooksResponse.getStatusCode());
        assertThat(findBooksResponse.getBody())
                .matches(r -> r.getTotalElements() == 3)
                .matches(r -> r.getTotalPages() == 1)
                .matches(r -> r.getContent().get(0).getTitle().equals(book3.getTitle()))
                .matches(r -> r.getContent().get(1).getTitle().equals(book2.getTitle()))
                .matches(r -> r.getContent().get(2).getTitle().equals(book1.getTitle()));
    }

    @Test
    void Given_GetBooksByTitleFirstPage_When_Handle_Then_OneBookWithMaxRateReturned() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Writer");
        author = authorRepository.save(author);

        BookEntity book1 = initBook(author, "title", 1, 1);
        BookEntity book2 = initBook(author, "title1", 3, 3);
        BookEntity book3 = initBook(author, "title2", 5, 5);
        BookEntity book4 = initBook(author, "test", 1, 1);

        bookRepository.saveAll(List.of(book1, book2, book3, book4));

        ResponseEntity<BookPageResponseDto> findBooksResponse = get(PATH + "?page=0&size=1&title=title", BookPageResponseDto.class);

        assertEquals(HttpStatus.OK, findBooksResponse.getStatusCode());
        assertThat(findBooksResponse.getBody())
                .matches(r -> r.getTotalElements() == 3)
                .matches(r -> r.getTotalPages() == 3)
                .matches(r -> r.getContent().get(0).getTitle().equals(book3.getTitle()));
    }

    @Test
    void Given_NoBooks_When_Handle_Then_EmptyListReturned() {
        ResponseEntity<BookPageResponseDto> findBooksResponse = get(PATH + "?page=0&size=10", BookPageResponseDto.class);
        assertEquals(HttpStatus.OK, findBooksResponse.getStatusCode());
        assertThat(findBooksResponse.getBody())
                .matches(r -> r.getTotalElements() == 0)
                .matches(r -> r.getTotalPages() == 0)
                .matches(r -> r.getPage() == 0)
                .matches(r -> r.getContent().size() == 0);
    }

    private BookEntity initBook(AuthorEntity author, String title, Integer totalRate, Integer averageRate) {
        BookEntity book = new BookEntity();
        book.setAuthor(author);
        book.setType(BookType.HORROR);
        book.setTitle(title);
        book.setRateCount(1);
        book.setTotalRate(totalRate);
        book.setAverageRate(averageRate);
        return book;
    }
}