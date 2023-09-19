package com.book.adapter.input.api;

import com.book.adapter.input.dto.AuthorDto;
import com.book.shared.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAuthorEndpointTest extends IntegrationTest {

    private static final String PATH = "/authors";

    @BeforeEach
    public void init() {
        resetDb();
    }

    @Test
    void Given_CreateValidAuthor_When_Handle_Then_CreatedAuthorReturned() {
        AuthorDto newAuthor = new AuthorDto();
        newAuthor.setName("Writer");
        ResponseEntity<AuthorDto> createAuthorResponse = post(PATH, newAuthor, AuthorDto.class);

        assertEquals(HttpStatus.OK, createAuthorResponse.getStatusCode());
        assertThat(createAuthorResponse.getBody())
                .hasNoNullFieldsOrProperties()
                .matches(r -> r.getName().equals(newAuthor.getName()));
        assertThat(authorRepository.findAuthorEntityByName(newAuthor.getName()).get()).isNotNull();
    }

    @Test
    void Given_CreateInvalidAuthor_When_Handle_Then_ErrorReturned() {
        ResponseEntity<AuthorDto> createAuthorResponse = post(PATH, new AuthorDto(), AuthorDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, createAuthorResponse.getStatusCode());
    }
}