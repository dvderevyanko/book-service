package com.book.adapter.input.mapper;

import com.book.adapter.input.dto.BookPageResponseDto;
import com.book.adapter.input.dto.BookResponseDto;
import com.book.adapter.input.dto.CreateBookDto;
import com.book.domain.common.Pagination;
import com.book.domain.model.Book;
import com.book.domain.model.BookType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookApiMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalRate", ignore = true)
    @Mapping(target = "rateCount", ignore = true)
    @Mapping(target = "averageRate", ignore = true)
    @Mapping(target = "author.id", source = "authorId")
    Book toDomain(CreateBookDto dto);

    default BookType toEnumType(String type) {
        return BookType.findTypeByName(type);
    }

    default String toStringType(BookType type) {
        return type.getName();
    }

    @Mapping(target = "page", source = "pageNumber")
    BookPageResponseDto toBookPage(Pagination<Book> pagination);

    @Mapping(target = "author.id", source = "author.id")
    @Mapping(target = "author.name", source = "author.name")
    BookResponseDto toDto(Book domain);

    List<BookResponseDto> toDtos(List<Book> domains);
}