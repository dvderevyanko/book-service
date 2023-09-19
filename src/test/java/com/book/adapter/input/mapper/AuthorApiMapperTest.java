package com.book.adapter.input.mapper;

import com.book.adapter.input.dto.AuthorDto;
import com.book.domain.model.Author;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorApiMapperTest {

    private final AuthorApiMapper target = new AuthorApiMapperImpl();

    @Test
    void Given_Author_ToDomain_Correct() {
        AuthorDto author = new AuthorDto();
        author.setId(1L);
        author.setName("name");
        var result = target.toDomain(author);
        assertThat(result)
                .matches(a -> a.getId() == null)
                .matches(a -> author.getName().equals(a.getName()));

    }

    @Test
    void Given_Author_ToDto_Correct() {
        Author author = new Author();
        author.setId(1L);
        author.setName("name");
        var result = target.toDto(author);
        assertThat(result)
                .matches(a -> author.getId().equals(a.getId()))
                .matches(a -> author.getName().equals(a.getName()));

    }

    @Test
    void Given_Author_ToDtos_Correct() {
        Author author = new Author();
        author.setId(1L);
        author.setName("name");
        var result = target.toDtos(List.of(author));
        assertThat(result)
                .hasSize(1)
                .first()
                .matches(a -> author.getId().equals(a.getId()))
                .matches(a -> author.getName().equals(a.getName()));
    }

}
