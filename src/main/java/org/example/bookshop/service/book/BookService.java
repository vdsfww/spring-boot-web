package org.example.bookshop.service.book;

import java.util.List;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> findByCategoryId(Long id, Pageable pageable);
}
