package org.example.bookshop.service;

import java.util.List;
import org.example.bookshop.dto.BookDto;
import org.example.bookshop.dto.CreateBookRequestDto;

public interface BookService {

	BookDto save(CreateBookRequestDto book);

	List<BookDto> findAll();

	BookDto getById(Long id);
}
