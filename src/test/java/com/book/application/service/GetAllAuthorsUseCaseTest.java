package com.book.application.service;

import com.book.application.port.output.AuthorRepositoryOPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllAuthorsUseCaseTest {

    @InjectMocks
    private GetAllAuthorsUseCase target;
    @Mock
    private AuthorRepositoryOPort repository;

    @Test
    void Given_GetAllAuth_When_Handle_Then_ListReturned() {
        when(repository.findAllAuthors()).thenReturn(Collections.emptyList());
        assertThat(target.handle()).isNotNull();
        verify(repository).findAllAuthors();
    }

}