package org.example.bookshop.service.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.exception.EntityNotFoundException;
import org.example.bookshop.mapper.BookMapper;
import org.example.bookshop.model.Book;
import org.example.bookshop.repository.book.BookRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookDto(savedBook);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id));
        return bookMapper.toBookDto(book);
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        bookMapper.updateBookFromDto(requestDto, book);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findAllByCategories_Id(id, pageable)
                .stream()
                .map(bookMapper::toBookWithoutCategoryId)
                .toList();
    }
}
