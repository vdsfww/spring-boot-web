package com.example.bookshop.service;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.entity.Book;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.repository.BookRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	@Override
	public List<BookDto> getAll() {
		List<Book> books = bookRepository.findAll();
		return bookMapper.toDtoList(books);
	}

	@Override
	public BookDto getById(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Book", id));
		return bookMapper.toDto(book);
	}

	@Override
	@Transactional
	public BookDto createBook(CreateBookRequestDto bookDto) {
		bookRepository.findByIsbn(bookDto.getIsbn()).ifPresent(b -> {
			throw new DataIntegrityViolationException("Book with same ISBN already exists");
		});

		Book book = bookMapper.toEntity(bookDto);
		Book saved = bookRepository.save(book);
		return bookMapper.toDto(saved);
	}
}
