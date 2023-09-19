package com.book.adapter.output.mapper;

import com.book.adapter.output.entity.AuthorEntity;
import com.book.adapter.output.entity.BookEntity;
import com.book.domain.model.Author;
import com.book.domain.model.Book;
import com.book.domain.model.BookType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BookEntityMapperTest {

    @InjectMocks
    private BookEntityMapperImpl target;
    @Spy
    private AuthorEntityMapperImpl authorEntityMapper;

    @Test
    void Given_AuthorEntity_ToDomain_Correct() {
        BookEntity book = initBookEntity();
        var result = target.toDomain(book);
        assertThat(result)
                .hasNoNullFieldsOrProperties().matches(a -> a.getId().equals(book.getId()))
                .matches(a -> a.getTitle().equals(book.getTitle()))
                .matches(a -> a.getType() == book.getType())
                .matches(a -> a.getAverageRate().equals(book.getAverageRate()))
                .matches(a -> a.getRateCount().equals(book.getRateCount()))
                .matches(a -> a.getTotalRate().equals(book.getTotalRate()))
                .matches(a -> a.getAuthor().getId().equals(book.getAuthor().getId()))
                .matches(a -> a.getAuthor().getName().equals(book.getAuthor().getName()));

    }

    @Test
    void Given_AuthorEntities_ToDomains_Correct() {
        BookEntity book = initBookEntity();
        var result = target.toDomains(List.of(book));
        assertThat(result)
                .hasSize(1)
                .first()
                .hasNoNullFieldsOrProperties().matches(a -> a.getId().equals(book.getId()))
                .matches(a -> a.getTitle().equals(book.getTitle()))
                .matches(a -> a.getType() == book.getType())
                .matches(a -> a.getAverageRate().equals(book.getAverageRate()))
                .matches(a -> a.getRateCount().equals(book.getRateCount()))
                .matches(a -> a.getTotalRate().equals(book.getTotalRate()))
                .matches(a -> a.getAuthor().getId().equals(book.getAuthor().getId()))
                .matches(a -> a.getAuthor().getName().equals(book.getAuthor().getName()));
    }

    @Test
    void Given_Author_ToEntity_Correct() {
        Book book = initBookDomain();
        var result = target.toEntity(book);
        assertThat(result)
                .hasNoNullFieldsOrProperties().matches(a -> a.getId().equals(book.getId()))
                .matches(a -> a.getTitle().equals(book.getTitle()))
                .matches(a -> a.getType() == book.getType())
                .matches(a -> a.getAverageRate().equals(book.getAverageRate()))
                .matches(a -> a.getRateCount().equals(book.getRateCount()))
                .matches(a -> a.getTotalRate().equals(book.getTotalRate()))
                .matches(a -> a.getAuthor().getId().equals(book.getAuthor().getId()))
                .matches(a -> a.getAuthor().getName().equals(book.getAuthor().getName()));

    }

    private BookEntity initBookEntity() {
        AuthorEntity author = new AuthorEntity();
        author.setId(1L);
        author.setName("name");
        BookEntity book = new BookEntity();
        book.setId(1L);
        book.setType(BookType.HORROR);
        book.setTitle("title");
        book.setAuthor(author);
        book.setAverageRate(5);
        book.setTotalRate(5);
        book.setRateCount(1);
        return book;
    }

    private Book initBookDomain() {
        Author author = new Author();
        author.setId(1L);
        author.setName("name");
        Book book = new Book();
        book.setId(1L);
        book.setType(BookType.HORROR);
        book.setTitle("title");
        book.setAuthor(author);
        book.setAverageRate(5);
        book.setTotalRate(5);
        book.setRateCount(1);
        return book;
    }
}
