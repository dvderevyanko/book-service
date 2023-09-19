package com.book.adapter.input.mapper;

import com.book.adapter.input.dto.AuthorDto;
import com.book.domain.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorApiMapper {

    @Mapping(target = "id", ignore = true)
    Author toDomain(AuthorDto dto);

    AuthorDto toDto(Author domains);

    List<AuthorDto> toDtos(List<Author> domains);
}