package org.example.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.service.book.BookService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Validated
@Tag(name = "Book management", description = "Endpoints for managing books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all available books")
    public List<BookDto> getAllBooks(@ParameterObject @PageableDefault Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Get one book according to its ID")
    public BookDto getById(@PathVariable @Positive Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new book",
            description = "Create a new book and save it to the DB")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Update an existing book from DB by its ID")
    public BookDto updateBook(@PathVariable @Positive Long id,
                              @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Delete one book from DB according to its ID")
    public void delete(@PathVariable @Positive Long id) {
        bookService.deleteById(id);
    }
}
