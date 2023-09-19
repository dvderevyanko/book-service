package com.book.application.service;

import com.book.application.port.input.CreateBookIPort;
import com.book.application.port.output.AuthorRepositoryOPort;
import com.book.application.port.output.BookRepositoryOPort;
import com.book.domain.exception.DuplicateException;
import com.book.domain.exception.InvalidDataException;
import com.book.domain.exception.ObjectNotFoundException;
import com.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CreateBookUseCase implements CreateBookIPort {

    private final static int FIELD_LENGTH = 50;
    private final static String TITLE_FIELD = "title";
    private final static String AUTHOR_FIELD = "author";
    private final static String TYPE_FIELD = "type";
    private final static Map<String, Predicate<Book>> BOOK_FIELD_CHECKS = Map.of(
            TITLE_FIELD, book -> book.getTitle() == null || book.getTitle().isEmpty() || book.getTitle().length() > FIELD_LENGTH,
            AUTHOR_FIELD, book -> book.getAuthor() == null || book.getAuthor().getId() == null,
            TYPE_FIELD, book -> book.getType() == null
    );

    private final BookRepositoryOPort bookRepository;
    private final AuthorRepositoryOPort authorRepository;

    @Override
    public Book handle(Book newBook, Integer rate) {
        validateBook(newBook, rate);
        RateBookUseCase.setRate(newBook, rate);
        Book book = bookRepository.saveBook(newBook);
        book.setAuthor(authorRepository.getAuthorById(book.getAuthor().getId()).get());
        return book;
    }

    private void validateBook(Book newBook, Integer rate) {
        List<String> invalidFields = new ArrayList<>(
                BOOK_FIELD_CHECKS.entrySet().stream()
                        .filter(e -> e.getValue().test(newBook))
                        .map(Map.Entry::getKey)
                        .toList()
        );
        if (!RateBookUseCase.isValidRate(rate, false)) {
            invalidFields.add(RateBookUseCase.RATE_FIELD);
        }
        if (!invalidFields.isEmpty()) {
            throw new InvalidDataException(invalidFields);
        } else {
            bookRepository.getBookByTitle(newBook.getTitle()).ifPresent(v -> {
                throw new DuplicateException();
            });
        }
        if (authorRepository.getAuthorById(newBook.getAuthor().getId()).isEmpty()) {
            throw new ObjectNotFoundException(AUTHOR_FIELD);
        }
    }

}
