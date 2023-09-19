package com.book.adapter.output.mapper;

import com.book.adapter.output.entity.AuthorEntity;
import com.book.domain.model.Author;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorEntityMapperTest {

    private final AuthorEntityMapper target = new AuthorEntityMapperImpl();

    @Test
    void Given_Author_ToDomain_Correct() {
        AuthorEntity author = new AuthorEntity();
        author.setId(1L);
        author.setName("name");
        var result = target.toDomain(author);
        assertThat(result)
                .matches(a -> author.getId().equals(a.getId()))
                .matches(a -> author.getName().equals(a.getName()));

    }

    @Test
    void Given_Author_ToEntity_Correct() {
        Author author = new Author();
        author.setId(1L);
        author.setName("name");
        var result = target.toEntity(author);
        assertThat(result)
                .matches(a -> author.getId().equals(a.getId()))
                .matches(a -> author.getName().equals(a.getName()));

    }

    @Test
    void Given_Author_ToDtos_Correct() {
        AuthorEntity author = new AuthorEntity();
        author.setId(1L);
        author.setName("name");
        var result = target.toDomains(List.of(author));
        assertThat(result)
                .hasSize(1)
                .first()
                .matches(a -> author.getId().equals(a.getId()))
                .matches(a -> author.getName().equals(a.getName()));
    }

}
