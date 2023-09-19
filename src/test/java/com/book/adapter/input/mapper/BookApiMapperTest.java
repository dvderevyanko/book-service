package com.book.adapter.input.mapper;

import com.book.adapter.input.dto.CreateBookDto;
import com.book.domain.common.Pagination;
import com.book.domain.model.Author;
import com.book.domain.model.Book;
import com.book.domain.model.BookType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookApiMapperTest {

    private final BookApiMapper target = new BookApiMapperImpl();

    @Test
    void Given_Author_ToDomain_Correct() {
        CreateBookDto book = new CreateBookDto();
        book.setType(BookType.HORROR.getName());
        book.setTitle("title");
        book.setRate(5);
        book.setAuthorId(1L);
        var result = target.toDomain(book);
        assertThat(result)
                .hasNoNullFieldsOrPropertiesExcept("id", "totalRate", "rateCount", "averageRate")
                .matches(a -> a.getTitle().equals(book.getTitle()))
                .matches(a -> a.getAuthor().getId().equals(book.getAuthorId()))
                .matches(a -> a.getAuthor().getName() == null)
                .matches(a -> a.getType() == BookType.HORROR);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Mystery", "Fantasy", "Horror"})
    void Given_ValidBookType_ToEnumType_Correct(String type) {
        var result = target.toEnumType(type);
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"123"})
    void Given_NotValidBookType_ToEnumType_NullReturned(String type) {
        var result = target.toEnumType(type);
        assertThat(result).isNull();
    }

    @Test
    void Given_ValidBookType_ToStringType_Correct() {
        var result = target.toStringType(BookType.HORROR);
        assertThat(result).isEqualTo(BookType.HORROR.getName());
    }

    @Test
    void Given_BookPagination_ToBookPage_Correct() {
        Book book = initBook();
        Pagination<Book> pagination = new Pagination<>(5, 10, "title");
        pagination.setContent(List.of(book));
        var result = target.toBookPage(pagination);
        assertThat(result)
                .hasNoNullFieldsOrPropertiesExcept("title", "page", "content")
                .matches(p -> p.getContent().size() == (pagination.getContent().size()))
                .matches(p -> p.getPage() == (pagination.getPageNumber()));
    }

    @Test
    void Given_Author_ToDto_Correct() {
        Book book = initBook();
        var result = target.toDto(book);
        assertThat(result)
                .hasNoNullFieldsOrProperties()
                .matches(a -> a.getId().equals(book.getId()))
                .matches(a -> a.getTitle().equals(book.getTitle()))
                .matches(a -> a.getType().equals(book.getType().getName()))
                .matches(a -> a.getAverageRate().equals(book.getAverageRate()))
                .matches(a -> a.getAuthor().getId().equals(book.getAuthor().getId()))
                .matches(a -> a.getAuthor().getName().equals(book.getAuthor().getName()));
    }

    @Test
    void Given_Author_ToDtos_Correct() {
        Book book = initBook();
        var result = target.toDtos(List.of(book));
        assertThat(result)
                .hasSize(1)
                .first()
                .matches(a -> a.getId().equals(book.getId()))
                .matches(a -> a.getTitle().equals(book.getTitle()))
                .matches(a -> a.getType().equals(book.getType().getName()))
                .matches(a -> a.getAverageRate().equals(book.getAverageRate()))
                .matches(a -> a.getAuthor().getId().equals(book.getAuthor().getId()))
                .matches(a -> a.getAuthor().getName().equals(book.getAuthor().getName()));
    }

    private Book initBook() {
        Author author = new Author();
        author.setId(1L);
        author.setName("name");
        Book book = new Book();
        book.setId(1L);
        book.setType(BookType.HORROR);
        book.setTitle("title");
        book.setAuthor(author);
        book.setAverageRate(1);
        return book;
    }
}
