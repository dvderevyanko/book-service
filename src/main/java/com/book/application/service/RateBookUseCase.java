package com.book.application.service;

import com.book.application.port.input.RateBookIPort;
import com.book.application.port.output.BookRepositoryOPort;
import com.book.domain.exception.BookNotFoundException;
import com.book.domain.exception.InvalidDataException;
import com.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class RateBookUseCase implements RateBookIPort {

    public static final String RATE_FIELD = "rate";
    private static final Pair<Integer, Integer> RATE_RANGE = Pair.of(1, 5);
    private static final Predicate<Integer> RATE_IN_RANGE =
            rate -> rate >= RATE_RANGE.getFirst() && rate <= RATE_RANGE.getSecond();
    private final BookRepositoryOPort repository;

    public static void setRate(Book book, Integer rate) {
        if (book.getRateCount() == null) {
            int newRate = rate == null ? 0 : rate;
            book.setTotalRate(newRate);
            book.setRateCount(1);
            book.setAverageRate(newRate);
        } else {
            book.setTotalRate(book.getTotalRate() + rate);
            book.setRateCount(book.getRateCount() + 1);
            book.setAverageRate(book.getTotalRate() / book.getRateCount());
        }
    }

    private static void validateRate(Integer rate) {
        if (!isValidRate(rate, true)) {
            throw new InvalidDataException(List.of(RATE_FIELD));
        }
    }

    public static boolean isValidRate(Integer rate, boolean isRequired) {
        if (isRequired && rate == null) {
            return false;
        } else return (rate == null || RATE_IN_RANGE.test(rate));
    }

    @Override
    public Book handle(Long bookId, Integer rate) {
        Book book = repository.getBookById(bookId).orElseThrow(BookNotFoundException::new);
        validateRate(rate);
        setRate(book, rate);
        return repository.saveBook(book);
    }
}
