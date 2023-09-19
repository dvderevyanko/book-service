package com.book.application.service;

import com.book.application.port.output.BookRepositoryOPort;
import com.book.domain.common.Pagination;
import com.book.domain.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindBooksUseCaseTest {

    @Captor
    ArgumentCaptor<Pagination<Book>> paginationCaptor;
    @InjectMocks
    private FindBooksUseCase target;
    @Mock
    private BookRepositoryOPort repository;

    @Test
    void Given_GetBooksContainingTitle_When_Handle_Then_ListReturned() {
        Pagination<Book> pagination = new Pagination<>(10, 10, "book");

        when(repository.findAllBooksByContainingTitle(pagination)).thenReturn(pagination);
        assertThat(target.handle(pagination)).isNotNull();

        verify(repository).findAllBooksByContainingTitle(paginationCaptor.capture());
        Pagination<Book> modifiedPagination = paginationCaptor.getValue();
        assertThat(modifiedPagination)
                .matches(p -> p.getSortBy().equals("averageRate"))
                .matches(p -> p.getSortDirection() == Sort.Direction.DESC);
    }

    @Test
    void Given_GetAllBooks_When_Handle_Then_ListReturned() {
        Pagination<Book> pagination = new Pagination<>(10, 10, null);

        when(repository.findAllBooks(pagination)).thenReturn(pagination);
        assertThat(target.handle(pagination)).isNotNull();

        verify(repository).findAllBooks(paginationCaptor.capture());
        Pagination<Book> modifiedPagination = paginationCaptor.getValue();
        assertThat(modifiedPagination)
                .matches(p -> p.getSortBy().equals("averageRate"))
                .matches(p -> p.getSortDirection() == Sort.Direction.DESC);
    }
}
