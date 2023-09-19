package com.book.adapter.input.api;

import com.book.adapter.input.dto.AuthorDto;
import com.book.adapter.input.mapper.AuthorApiMapper;
import com.book.application.port.input.CreateAuthorIPort;
import com.book.domain.model.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CreateAuthorEndpoint {

    private final AuthorApiMapper mapper;
    private final CreateAuthorIPort createAuthorUseCase;

    @PostMapping(ApiConstants.AUTHOR_API_PATH)
    public AuthorDto createAuthor(@RequestBody AuthorDto request) {
        log.info("Received creation request for author : {}", request.getName());
        Author createdAuthor = createAuthorUseCase.handle(mapper.toDomain(request));
        return mapper.toDto(createdAuthor);
    }
}
