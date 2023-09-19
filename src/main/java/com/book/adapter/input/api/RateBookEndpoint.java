package com.book.adapter.input.api;

import com.book.adapter.input.dto.BookRateDto;
import com.book.adapter.input.dto.BookResponseDto;
import com.book.adapter.input.mapper.BookApiMapper;
import com.book.application.port.input.RateBookIPort;
import com.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RateBookEndpoint {

    private final BookApiMapper mapper;
    private final RateBookIPort rateBookUseCase;

    @PatchMapping(ApiConstants.BOOK_API_PATH + "/{bookId}")
    public BookResponseDto rateBook(@PathVariable Long bookId, @RequestBody BookRateDto request) {
        log.info("Received new rate request for book : {} with value : {}", bookId, request.getRate());
        Book updatedBook = rateBookUseCase.handle(bookId, request.getRate());
        return mapper.toDto(updatedBook);
    }
}
