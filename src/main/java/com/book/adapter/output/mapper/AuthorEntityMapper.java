package com.book.adapter.output.mapper;

import com.book.adapter.output.entity.AuthorEntity;
import com.book.domain.model.Author;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorEntityMapper {

    Author toDomain(AuthorEntity entity);

    AuthorEntity toEntity(Author domain);

    List<Author> toDomains(List<AuthorEntity> entities);

}