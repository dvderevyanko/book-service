package com.book.application.service;

import com.book.application.port.output.BookRepositoryOPort;
import com.book.domain.exception.InvalidDataException;
import com.book.domain.model.Book;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateBookUseCaseTest {

    @Captor
    ArgumentCaptor<Book> bookCaptor;
    @InjectMocks
    private RateBookUseCase target;
    @Mock
    private BookRepositoryOPort repository;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void Given_ValidRateForNoRatingBook_When_Handle_Then_BookRateUpdated(Integer rate) {
        Book book = createBook(0, 0);
        when(repository.getBookById(eq(book.getId()))).thenReturn(Optional.of(book));
        when(repository.saveBook(eq(book))).thenReturn(book);
        assertThat(target.handle(book.getId(), rate))
                .matches(b -> b.getAverageRate().equals(rate));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, 0, 6})
    void Given_InvalidRate_When_Handle_Then_ErrorThrown(Integer rate) {
        Book book = createBook(0, 0);
        when(repository.getBookById(eq(book.getId()))).thenReturn(Optional.of(book));
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> target.handle(book.getId(), rate))
                .withMessage("Invalid fields : rate");
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,5,3",
            "2,2,5,2",
            "7,18,2,2"
    })
    void Given_ValidRateForRatedBook_When_Handle_Then_BookRateUpdated(Integer rateCount,
            Integer totalRate, Integer newRate, Integer expectedRate) {
        Book book = createBook(rateCount, totalRate);
        when(repository.getBookById(eq(book.getId()))).thenReturn(Optional.of(book));
        when(repository.saveBook(eq(book))).thenReturn(book);
        target.handle(book.getId(), newRate);
        verify(repository).saveBook(bookCaptor.capture());
        assertThat(bookCaptor.getValue())
                .matches(b -> b.getRateCount().equals(rateCount + 1))
                .matches(b -> b.getTotalRate().equals(totalRate + newRate))
                .matches(b -> b.getAverageRate().equals(expectedRate));
    }

    private Book createBook(Integer rateCount, Integer totalRate) {
        Book book = new Book();
        book.setRateCount(rateCount);
        book.setTotalRate(totalRate);
        return book;
    }

}