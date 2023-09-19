package com.book.adapter.input.api;

import com.book.adapter.input.dto.AuthorDto;
import com.book.adapter.input.mapper.AuthorApiMapper;
import com.book.application.port.input.GetAllAuthorsIPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GetAllAuthorsEndpoint {

    private final AuthorApiMapper mapper;
    private final GetAllAuthorsIPort getAllAuthorsUseCase;

    @GetMapping(ApiConstants.AUTHOR_API_PATH)
    public List<AuthorDto> getAuthors() {
        log.info("Received get all authors request");
        return mapper.toDtos(getAllAuthorsUseCase.handle());
    }
}
