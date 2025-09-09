package com.example.bookshop.controller;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.service.BookService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;

	@GetMapping
	public List<BookDto> getAll() {
		return bookService.getAll();
	}

	@GetMapping("/{id}")
	public BookDto getBookById(@PathVariable Long id) {
		return bookService.getById(id);
	}

	@PostMapping
	public ResponseEntity<BookDto> createBook(@Valid @RequestBody
											  CreateBookRequestDto request) {
		BookDto created = bookService.createBook(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(location).body(created);
	}
}
