package com.book.adapter.input.api;

import com.book.adapter.input.dto.BookResponseDto;
import com.book.adapter.input.dto.CreateBookDto;
import com.book.adapter.input.mapper.BookApiMapper;
import com.book.application.port.input.CreateBookIPort;
import com.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CreateBookEndpoint {

    private final BookApiMapper mapper;
    private final CreateBookIPort createBookUseCase;

    @PostMapping(ApiConstants.BOOK_API_PATH)
    public BookResponseDto createBook(@RequestBody CreateBookDto request) {
        log.info("Received creation request for book : {}", request.getTitle());
        Book createdBook = createBookUseCase.handle(mapper.toDomain(request), request.getRate());
        return mapper.toDto(createdBook);
    }
}
