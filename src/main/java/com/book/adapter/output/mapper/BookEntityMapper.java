package com.book.adapter.output.mapper;

import com.book.adapter.output.entity.BookEntity;
import com.book.domain.model.Book;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = AuthorEntityMapper.class)
public interface BookEntityMapper {

    Book toDomain(BookEntity entity);

    BookEntity toEntity(Book domain);

    List<Book> toDomains(List<BookEntity> entities);

}