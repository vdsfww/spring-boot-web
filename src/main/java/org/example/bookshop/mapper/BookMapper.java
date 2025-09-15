package org.example.bookshop.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.model.Book;
import org.example.bookshop.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    Book toBook(CreateBookRequestDto requestDto);

    void updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toBookWithoutCategoryId(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> ids = book.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(ids);
    }

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto bookRequestDto) {
        if (bookRequestDto.getCategoryIds() != null) {
            book.setCategories(bookRequestDto
                    .getCategoryIds()
                    .stream()
                    .map(Category::new)
                    .collect(Collectors.toSet()));
        }
    }
}
