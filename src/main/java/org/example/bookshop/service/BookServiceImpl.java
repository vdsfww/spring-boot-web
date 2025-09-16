package org.example.bookshop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.BookDto;
import org.example.bookshop.dto.CreateBookRequestDto;
import org.example.bookshop.exception.EntityNotFoundException;
import org.example.bookshop.mapper.BookMapper;
import org.example.bookshop.model.Book;
import org.example.bookshop.repository.BookRepository;

@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	@Override
	public BookDto save(CreateBookRequestDto requestDto) {
		Book book = bookMapper.toModel(requestDto);
		bookRepository.save(book);
		return bookMapper.toDto(book);
	}

	@Override
	public List<BookDto> findAll() {
		return bookRepository.findAll()
				.stream()
				.map(bookMapper::toDto)
				.toList();
	}

	@Override
	public BookDto getById(Long id) {
		return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Can't find book by id" + id)
		));
	}
}
