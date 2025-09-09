package com.example.bookshop.mapper;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.entity.Book;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
	BookDto toDto(Book book);

	List<BookDto> toDtoList(List<Book> books);

	Book toEntity(CreateBookRequestDto dto);
}
